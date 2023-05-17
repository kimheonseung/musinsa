package com.devh.project.musinsa.aggregator.domain.price.lowest.entity;

import com.devh.project.musinsa.aggregator.domain.projection.LowestPriceItemByCategoryProjection;
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

    public static LowestPriceItemByCategory createByProjection(LowestPriceItemByCategoryProjection projection) {
        return new LowestPriceItemByCategory(projection.getCategoryName(), projection.getBrandName(), projection.getLowestPrice());
    }
}
