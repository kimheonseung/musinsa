package com.devh.project.musinsa.search.domain.price.comparison.controller;

import com.devh.project.musinsa.search.domain.price.comparison.dto.ComparisonPriceItemByCategoryResponse;
import com.devh.project.musinsa.search.domain.price.comparison.service.ComparisonPriceItemByCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/api/v1/price/comparison")
public class ComparisonPriceController {
    private final ComparisonPriceItemByCategoryService comparisonPriceItemByCategoryService;

    public ComparisonPriceController(ComparisonPriceItemByCategoryService comparisonPriceItemByCategoryService) {
        this.comparisonPriceItemByCategoryService = comparisonPriceItemByCategoryService;
    }

    @GetMapping("/category")
    public ComparisonPriceItemByCategoryResponse getComparisonPriceItemByCategory(@RequestParam("category") String categoryName) {
        return comparisonPriceItemByCategoryService.getByCategory(categoryName);
    }
}
