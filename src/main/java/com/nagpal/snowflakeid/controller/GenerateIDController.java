package com.nagpal.snowflakeid.controller;

import com.nagpal.snowflakeid.entity.SnowFlakeId;
import com.nagpal.snowflakeid.service.IDGeneratorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class GenerateIDController {

    @Autowired
    private IDGeneratorService idGeneratorService;

    @GetMapping("/generate/id/v1")
    public ResponseEntity<Long> generateID() {
        long id = idGeneratorService.generateID().getUnique_id();
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/generate/id/v1/batch")
    public ResponseEntity<List<Long>> generateIDs(@RequestParam String size) {

        long startTime = System.currentTimeMillis();
        List<SnowFlakeId> ids = idGeneratorService.generateIDs(Integer.parseInt(size));
        log.info("ID generation and save time : {}", (System.currentTimeMillis() - startTime));
        return ResponseEntity.ok().body(ids.stream().map(SnowFlakeId::getUnique_id).toList());
    }
}
