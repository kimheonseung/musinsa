package com.devh.project.musinsa.search.domain.price.comparison.service;

import com.devh.project.musinsa.search.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.search.domain.common.exception.SearchException;
import com.devh.project.musinsa.search.domain.price.comparison.dto.ComparisonPriceItemByCategoryResponse;
import com.devh.project.musinsa.search.domain.price.comparison.repository.ComparisonPriceItemByCategoryRepository;
import org.springframework.stereotype.Service;

/**
 * 3번 요구사항.
 * - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하기 위한 클래스
 */
@Service
public class ComparisonPriceItemByCategoryServiceImpl implements ComparisonPriceItemByCategoryService {

    private final ComparisonPriceItemByCategoryRepository comparisonPriceItemByCategoryRepository;

    public ComparisonPriceItemByCategoryServiceImpl(ComparisonPriceItemByCategoryRepository comparisonPriceItemByCategoryRepository) {
        this.comparisonPriceItemByCategoryRepository = comparisonPriceItemByCategoryRepository;
    }

    @Override
    public ComparisonPriceItemByCategoryResponse getByCategory(String categoryName) {
        if (!comparisonPriceItemByCategoryRepository.existsById(categoryName)) {
            throw new SearchException(ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR);
        }
        return ComparisonPriceItemByCategoryResponse.create(comparisonPriceItemByCategoryRepository.findById(categoryName).orElseThrow());
    }
}
