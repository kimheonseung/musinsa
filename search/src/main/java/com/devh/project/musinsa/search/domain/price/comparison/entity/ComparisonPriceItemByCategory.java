package com.devh.project.musinsa.search.domain.price.comparison.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;
import java.util.Set;

@Getter
@RedisHash("search:price:comparison:category")
public class ComparisonPriceItemByCategory {

    @Id
    private String categoryName;
    private Set<BrandItem> lowestPriceBrandItems;
    private Set<BrandItem> highestPriceBrandItems;

    private ComparisonPriceItemByCategory(String categoryName,
                                          Set<BrandItem> lowestPriceBrandItems,
                                          Set<BrandItem> highestPriceBrandItems) {
        this.categoryName = categoryName;
        this.lowestPriceBrandItems = lowestPriceBrandItems;
        this.highestPriceBrandItems = highestPriceBrandItems;
    }

    public static ComparisonPriceItemByCategory create(String categoryName,
                                                       Set<BrandItem> lowestPriceBrandItems,
                                                       Set<BrandItem> highestPriceBrandItems) {
        // T
        return new ComparisonPriceItemByCategory(categoryName, lowestPriceBrandItems, highestPriceBrandItems);
    }

    @Getter
    public static class BrandItem {
        private final String brandName;
        private final long price;

        private BrandItem(String brandName, long price) {
            this.brandName = brandName;
            this.price = price;
        }

        public static BrandItem create(String brandName, long price) {
            return new BrandItem(brandName, price);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BrandItem brandItem = (BrandItem) o;
            return Objects.equals(brandName, brandItem.brandName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(brandName);
        }
    }
}
