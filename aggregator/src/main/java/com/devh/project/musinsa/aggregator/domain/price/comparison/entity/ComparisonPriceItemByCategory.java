package com.devh.project.musinsa.aggregator.domain.price.comparison.entity;

import com.devh.project.musinsa.aggregator.domain.projection.ComparisonPriceItemProjection;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
                                                       List<ComparisonPriceItemProjection> projections) {
        Map<String, List<ComparisonPriceItemProjection>> lowestMap =
                projections.stream().collect(Collectors.groupingBy(ComparisonPriceItemProjection::getLowestBrandName));

        Map<String, List<ComparisonPriceItemProjection>> highestMap =
                projections.stream().collect(Collectors.groupingBy(ComparisonPriceItemProjection::getHighestBrandName));

        Set<BrandItem> lowestItems = new HashSet<>();
        for (String brandName : lowestMap.keySet()) {
            lowestItems.addAll(
                    lowestMap.get(brandName).stream().map(p -> BrandItem.create(p.getLowestBrandName(), p.getLowestPrice())).collect(Collectors.toList())
            );
        }

        Set<BrandItem> highestItems = new HashSet<>();
        for (String brandName : highestMap.keySet()) {
            highestItems.addAll(
                    highestMap.get(brandName).stream().map(p -> BrandItem.create(p.getHighestBrandName(), p.getHighestPrice())).collect(Collectors.toList())
            );
        }
        return new ComparisonPriceItemByCategory(categoryName, lowestItems, highestItems);
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
