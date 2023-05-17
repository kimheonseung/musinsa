package com.devh.project.musinsa.search.domain.price.lowest.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "search:price:lowest:category")
public class LowestPriceItemByCategory {

    @Id
    private String categoryName;
    private String brandName;
    private Long price;

    private LowestPriceItemByCategory(String categoryName, String brandName, Long price) {
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.price = price;
    }

    public static LowestPriceItemByCategory create(String categoryName, String brandName, long price) {
        // T
        return new LowestPriceItemByCategory(categoryName, brandName, price);
    }
}
