package com.nagpal.snowflakeid.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateIDController {

    @GetMapping("/generate/id/v1")
    public ResponseEntity<String> generateID() {
        return ResponseEntity.ok().body("ID Generated !!!");
    }
}
