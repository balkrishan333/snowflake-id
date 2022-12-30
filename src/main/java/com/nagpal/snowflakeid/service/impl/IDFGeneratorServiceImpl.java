package com.nagpal.snowflakeid.service.impl;

import com.nagpal.snowflakeid.config.Configuration;
import com.nagpal.snowflakeid.repository.IDGeneratorRepo;
import com.nagpal.snowflakeid.entity.SnowFlakeId;
import com.nagpal.snowflakeid.service.IDGeneratorService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.postgresql.copy.CopyManager;
import org.postgresql.jdbc.PgConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class IDFGeneratorServiceImpl implements IDGeneratorService {

    @Autowired
    private Configuration config;
    @Autowired
    private IDGeneratorRepo generatorRepo;
    @Autowired
    private IDGeneratorService self;

    private long prevMS = 0;
    private int prevCounter = 0;

    private static final String SQL = "COPY snowflake_id (unique_id) FROM STDIN WITH " +
            "                               (FORMAT TEXT, ENCODING 'UTF-8', DELIMITER '\t', HEADER false)";

    /*
        Size - 64 bit
        41 bit - time in ms
        10 bit - Machine id
        12 bit - MS counter
        1 bit -  reserved
     */
    @Override
    @Transactional
    public SnowFlakeId generateID() {
        return generateIDs(1).get(0);
    }

    @Override
    @Transactional
    public List<SnowFlakeId> generateIDs(int batchSize) {
        if (batchSize < 1) {
            throw new IllegalArgumentException(String.format("Invalid batch size : %s" , batchSize));
        }

        List<SnowFlakeId> ids = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < batchSize; i++) {
            ids.add(generateIdInternal());
        }
        log.info("ID generation time : {}", (System.currentTimeMillis() - startTime));
        generatorRepo.saveAll(ids);
        return ids;
    }

    @Override
    public void copySave(int batchSize) {
        Connection connection = get();
        PgConnection unwrapped;
        try {
            unwrapped = connection.unwrap(PgConnection.class);
            CopyManager copyManager = unwrapped.getCopyAPI();

            List<SnowFlakeId> ids = new ArrayList<>();
            for (int i = 0; i < batchSize; i++) {
                ids.add(generateIdInternal());
            }

            StringBuilder sb = new StringBuilder();

            ids.forEach(id -> {
                sb.append(id.getUnique_id()).append("\n");
            });

            log.debug(sb.toString());

            InputStream inputStream = new ByteArrayInputStream(sb.toString().getBytes());
            copyManager.copyIn(SQL, inputStream);
            connection.close(); // TODO: haven't tested after this
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection get() {
        String driver = "org.postgresql.Driver";
        try {
            try {
                Class.forName(driver).getDeclaredConstructor().newInstance();
            } catch (InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/snowflakeid?reWriteBatchedInserts=true",
                                                    "postgres", "password");

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException("Can not instantiate driver " + driver, ex);
        } catch (SQLException ex) {
            throw new RuntimeException("Can not connect to database", ex);
        }
    }

    private SnowFlakeId generateIdInternal() {
        log.debug("Generating ID...");
        long timeMS = System.currentTimeMillis();
        int machineID = config.getMachineId();

        StringBuilder sb = new StringBuilder();
        //reserve bit at starting
        sb.append("0");

        sb.append(Long.toBinaryString(timeMS)); // 41 bits time in ms

        String machineIDBinary  = Integer.toBinaryString(machineID);
        machineIDBinary = String.format("%10s", machineIDBinary).replace(' ', '0'); //10 bits machine id
        sb.append(machineIDBinary);

        int counter = prevCounter;
        if (timeMS == prevMS) {
            counter++;
        } else {
            counter = 0;
            prevMS = timeMS;
        }
        prevCounter = counter;

        //12 bit counter
        String counterBits = String.format("%12s", Integer.toBinaryString(counter)).replace(' ', '0');
        sb.append(counterBits);

        log.debug("Binary string : {}", sb.toString());
        long id = Long.parseLong(sb.toString(), 2);
        log.debug("ID : {}", id);

        SnowFlakeId flakeId = new SnowFlakeId();
        flakeId.setUnique_id(id);

        return flakeId;
    }
}
