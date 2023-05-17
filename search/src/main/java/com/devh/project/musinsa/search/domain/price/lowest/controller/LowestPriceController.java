package com.devh.project.musinsa.search.domain.price.lowest.controller;

import com.devh.project.musinsa.search.domain.price.lowest.dto.LowestPriceItemByBrandResponse;
import com.devh.project.musinsa.search.domain.price.lowest.dto.LowestPriceItemByCategoryResponse;
import com.devh.project.musinsa.search.domain.price.lowest.service.LowestPriceItemByBrandService;
import com.devh.project.musinsa.search.domain.price.lowest.service.LowestPriceItemByCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/api/v1/price/lowest")
public class LowestPriceController {

    private final LowestPriceItemByBrandService lowestPriceItemByBrandService;
    private final LowestPriceItemByCategoryService lowestPriceItemByCategoryService;

    public LowestPriceController(LowestPriceItemByBrandService lowestPriceItemByBrandService,
                                 LowestPriceItemByCategoryService lowestPriceItemByCategoryService) {
        this.lowestPriceItemByBrandService = lowestPriceItemByBrandService;
        this.lowestPriceItemByCategoryService = lowestPriceItemByCategoryService;
    }

    @GetMapping("/category")
    public LowestPriceItemByCategoryResponse getLowestPriceByCategory() {
        return lowestPriceItemByCategoryService.get();
    }

    @GetMapping("/brand")
    public LowestPriceItemByBrandResponse getLowestPriceItemByBrand() {
        return lowestPriceItemByBrandService.get();
    }
}
