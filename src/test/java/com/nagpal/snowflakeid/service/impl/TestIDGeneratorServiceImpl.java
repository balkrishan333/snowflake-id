package com.nagpal.snowflakeid.service.impl;

import com.nagpal.snowflakeid.service.IDGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestIDGeneratorServiceImpl {

    @Autowired
    private IDGeneratorService idGeneratorService;

    @Test
    public void generateID() {
        int size = 15;
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ids.add(idGeneratorService.generateID());
        }

        Assertions.assertEquals(size, ids.size());
        System.out.println(ids);
    }
}
