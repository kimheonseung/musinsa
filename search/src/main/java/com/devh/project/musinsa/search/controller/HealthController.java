package com.devh.project.musinsa.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/v1/api/health")
public class HealthController {
    @GetMapping
    public boolean getHealth() {
        return true;
    }
}
