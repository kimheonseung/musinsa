package com.devh.project.musinsa.aggregator.domain.projection;

public interface LowestPriceItemByCategoryProjection {
    String getBrandName();
    String getCategoryName();
    long getLowestPrice();
}
