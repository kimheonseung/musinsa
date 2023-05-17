package com.devh.project.musinsa.backoffice.controller;

import com.devh.project.musinsa.backoffice.domain.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backoffice/v1/api/health")
public class HealthController {
    @GetMapping
    public ApiResponse<Boolean> getHealth() {
        return ApiResponse.create(true);
    }
}
