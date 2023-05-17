package com.devh.project.musinsa.backoffice.domain.brand.service;

import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandAddRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDTO;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDeleteRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandUpdateRequest;
import com.devh.project.musinsa.backoffice.domain.brand.entity.Brand;
import com.devh.project.musinsa.backoffice.domain.brand.repository.BrandRepository;
import com.devh.project.musinsa.backoffice.domain.common.exception.BrandException;
import com.devh.project.musinsa.backoffice.domain.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandDTO add(BrandAddRequest brandAddRequest) {
        if (this.brandRepository.existsByName(brandAddRequest.getName())) {
            throw new BrandException(ErrorCode.BRAND_DUPLICATE_ERROR);
        }
        Brand savedBrand = this.brandRepository.save(Brand.newBrand(brandAddRequest.getName()));
        BrandDTO brandDTO = BrandDTO.create(savedBrand.getId(), savedBrand.getName());
        return brandDTO;
    }

    @Override
    @Transactional
    public BrandDTO update(BrandUpdateRequest brandUpdateRequest) {
        Brand brand = this.brandRepository.findById(brandUpdateRequest.getId()).orElseThrow(
                () -> new BrandException(ErrorCode.BRAND_NOT_FOUND_ERROR)
        );
        if (this.brandRepository.existsByName(brandUpdateRequest.getName())) {
            throw new BrandException(ErrorCode.BRAND_DUPLICATE_ERROR);
        }
        brand.updateName(brandUpdateRequest.getName());
        BrandDTO brandDTO = BrandDTO.create(brand.getId(), brand.getName());
        return brandDTO;
    }

    @Override
    public BrandDTO delete(BrandDeleteRequest brandDeleteRequest) {
        Brand brand = this.brandRepository.findById(brandDeleteRequest.getId()).orElseThrow(
                () -> new BrandException(ErrorCode.BRAND_NOT_FOUND_ERROR)
        );
        BrandDTO brandDTO = BrandDTO.create(brand.getId(), brand.getName());
        this.brandRepository.deleteById(brandDeleteRequest.getId());
        return brandDTO;
    }
}
