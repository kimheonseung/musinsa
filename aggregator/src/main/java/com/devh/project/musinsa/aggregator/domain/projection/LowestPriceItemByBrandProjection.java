package com.devh.project.musinsa.aggregator.domain.projection;

public interface LowestPriceItemByBrandProjection {
    long getPrice();
    String getBrandName();
    String getCategoryName();
}
