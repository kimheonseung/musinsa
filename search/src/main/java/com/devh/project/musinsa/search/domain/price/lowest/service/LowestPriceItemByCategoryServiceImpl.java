package com.devh.project.musinsa.search.domain.price.lowest.service;

import com.devh.project.musinsa.search.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.search.domain.common.exception.SearchException;
import com.devh.project.musinsa.search.domain.price.lowest.dto.LowestPriceItemByCategoryResponse;
import com.devh.project.musinsa.search.domain.price.lowest.entity.LowestPriceItemByCategory;
import com.devh.project.musinsa.search.domain.price.lowest.repository.LowestPriceItemByCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 1번 요구사항.
 * - 카테고리 별 최저가격 브랜드와 상품 가격, 총액 조회를 위한 클래스
 */
@Service
public class LowestPriceItemByCategoryServiceImpl implements LowestPriceItemByCategoryService {

    private final LowestPriceItemByCategoryRepository lowestPriceItemByCategoryRepository;

    public LowestPriceItemByCategoryServiceImpl(LowestPriceItemByCategoryRepository lowestPriceItemByCategoryRepository) {
        this.lowestPriceItemByCategoryRepository = lowestPriceItemByCategoryRepository;
    }

    @Override
    public LowestPriceItemByCategoryResponse get() {
        List<LowestPriceItemByCategory> results = new ArrayList<>();
        lowestPriceItemByCategoryRepository.findAll().forEach(results::add);
        if (results.size() == 0) {
            throw new SearchException(ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR);
        }
        return LowestPriceItemByCategoryResponse.create(results);
    }
}
