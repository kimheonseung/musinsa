package com.devh.project.musinsa.search.domain.price.lowest.service;

import com.devh.project.musinsa.search.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.search.domain.common.exception.SearchException;
import com.devh.project.musinsa.search.domain.price.lowest.dto.LowestPriceItemByBrandResponse;
import com.devh.project.musinsa.search.domain.price.lowest.entity.LowestPriceItemByBrand;
import com.devh.project.musinsa.search.domain.price.lowest.repository.LowestPriceItemByBrandRepository;
import org.springframework.stereotype.Service;

/**
 * 2번 요구사항
 * - 단일 브랜드로 모든 카테고리 상품 구매 시 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하기 위한 클래스
 */
@Service
public class LowestPriceItemByBrandServiceImpl implements LowestPriceItemByBrandService {

    private final LowestPriceItemByBrandRepository lowestPriceItemByBrandRepository;

    public LowestPriceItemByBrandServiceImpl(LowestPriceItemByBrandRepository lowestPriceItemByBrandRepository) {
        this.lowestPriceItemByBrandRepository = lowestPriceItemByBrandRepository;
    }

    @Override
    public LowestPriceItemByBrandResponse get() {
        LowestPriceItemByBrand lowestPriceItemByBrand = lowestPriceItemByBrandRepository.findById("brand").orElseThrow(
                () -> new SearchException(ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR)
        );
        return LowestPriceItemByBrandResponse.create(lowestPriceItemByBrand);
    }
}
