package com.nagpal.snowflakeid.service.impl;

import com.nagpal.snowflakeid.Configuration;
import com.nagpal.snowflakeid.service.IDGeneratorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class IDFGeneratorServiceImpl implements IDGeneratorService {

    @Autowired
    private Configuration config;
    @Override
    public long generateID() {
        log.info("Entered generateID...");
        long timeMS = System.currentTimeMillis();
        int machineID = config.getMachineId();

        StringBuilder sb = new StringBuilder();
        sb.append(Long.toBinaryString(timeMS));

        String machineIDBinary  = Integer.toBinaryString(machineID);
        machineIDBinary = String.format("%10s", machineIDBinary).replace(' ', '0');

        sb.append(machineIDBinary);

        log.info("Binary string : {}", sb.toString());
        long id = Long.parseLong(sb.toString(), 2);
        log.info("ID : {}", id);
        return id;
    }
}
