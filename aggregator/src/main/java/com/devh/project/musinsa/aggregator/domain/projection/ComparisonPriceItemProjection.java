package com.devh.project.musinsa.aggregator.domain.projection;

public interface ComparisonPriceItemProjection {
    String getLowestBrandName();
    Long getLowestPrice();
    String getHighestBrandName();
    Long getHighestPrice();
    String getCategoryName();
}
