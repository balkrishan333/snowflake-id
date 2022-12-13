package com.nagpal.snowflakeid.service.impl;

import com.nagpal.snowflakeid.service.IDGeneratorService;
import org.springframework.stereotype.Service;

@Service
public class IDFGeneratorServiceImpl implements IDGeneratorService {
    @Override
    public String generateID() {
        return "ID generated from Service !!!";
    }
}
