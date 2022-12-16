package com.nagpal.snowflakeid.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestIDGeneratorServiceImpl {

    @Test
    public void generateID() {
        long timeMS = System.currentTimeMillis();
        String binary = Long.toBinaryString(timeMS);
        System.out.println(binary);
    }
}
