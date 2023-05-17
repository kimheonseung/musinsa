package com.devh.project.musinsa.search.domain.price.comparison.service;

import com.devh.project.musinsa.search.domain.price.comparison.dto.ComparisonPriceItemByCategoryResponse;

public interface ComparisonPriceItemByCategoryService {
    ComparisonPriceItemByCategoryResponse getByCategory(String categoryName);
}
