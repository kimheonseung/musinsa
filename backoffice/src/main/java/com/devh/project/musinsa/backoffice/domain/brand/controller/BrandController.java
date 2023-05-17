package com.devh.project.musinsa.backoffice.domain.brand.controller;

import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandAddRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDTO;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDeleteRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandUpdateRequest;
import com.devh.project.musinsa.backoffice.domain.brand.service.BrandService;
import com.devh.project.musinsa.backoffice.domain.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/backoffice/api/v1/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PutMapping
    public ApiResponse<BrandDTO> addBrand(@RequestBody @Valid BrandAddRequest brandAddRequest) {
        return ApiResponse.create(this.brandService.add(brandAddRequest));
    }

    @PostMapping
    public ApiResponse<BrandDTO> updateBrand(@RequestBody @Valid BrandUpdateRequest brandUpdateRequest) {
        return ApiResponse.create(this.brandService.update(brandUpdateRequest));
    }

    @DeleteMapping
    public ApiResponse<BrandDTO> deleteBrand(@RequestBody @Valid BrandDeleteRequest brandDeleteRequest) {
        return ApiResponse.create(this.brandService.delete(brandDeleteRequest));
    }
}
