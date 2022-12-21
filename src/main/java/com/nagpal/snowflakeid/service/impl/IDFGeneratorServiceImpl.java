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

    private long prevMS = 0;
    private int prevCounter = 0;

    @Override
    /*
        Size - 64 bit
        41 bit - time in ms
        10 bit - Machine id
        12 bit - MS counter
        1 bit -  reserved
     */
    public long generateID() {
        log.info("Entered generateID...");
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

        log.info("Binary string : {}", sb.toString());
        long id = Long.parseLong(sb.toString(), 2);
        log.info("ID : {}", id);
        return id;
    }
}
