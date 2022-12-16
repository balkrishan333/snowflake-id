package com.nagpal.snowflakeid.controller;

import com.nagpal.snowflakeid.service.IDGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateIDController {

    @Autowired
    private IDGeneratorService idGeneratorService;

    @GetMapping("/generate/id/v1")
    public ResponseEntity<Long> generateID() {
        long id = idGeneratorService.generateID();
        return ResponseEntity.ok().body(id);
    }
}
