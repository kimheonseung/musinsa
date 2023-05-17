package com.devh.project.musinsa.backoffice.domain.brand.service;

import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandAddRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDTO;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDeleteRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandUpdateRequest;

public interface BrandService {
    BrandDTO add(BrandAddRequest brandAddRequest);
    BrandDTO update(BrandUpdateRequest brandUpdateRequest);
    BrandDTO delete(BrandDeleteRequest brandDeleteRequest);
}
