package com.devh.project.musinsa.aggregator.domain.price.lowest.entity;

import com.devh.project.musinsa.aggregator.domain.projection.LowestPriceItemByBrandProjection;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter
@RedisHash(value = "search:price:lowest")
public class LowestPriceItemByBrand {
    @Id
    private String id = "brand";
    private String brandName;
    private List<CategoryItem> categoryItems;
    private Long totalPrice;

    private LowestPriceItemByBrand(String brandName,
                                   List<CategoryItem> categoryItems,
                                   long totalPrice) {
        this.brandName = brandName;
        this.categoryItems = categoryItems;
        this.totalPrice = totalPrice;
    }

    public static LowestPriceItemByBrand create(String brandName, List<LowestPriceItemByBrandProjection> items, long totalPrice) {
        List<CategoryItem> tmpCategoryItems = new ArrayList<>();
        for (LowestPriceItemByBrandProjection item : items) {
            tmpCategoryItems.add(CategoryItem.create(item.getCategoryName(), item.getPrice()));
        }
        return new LowestPriceItemByBrand(brandName, tmpCategoryItems, totalPrice);
    }

    @Getter
    public static class CategoryItem {
        private final String categoryName;
        private final Long price;

        private CategoryItem(String categoryName, Long price) {
            this.categoryName = categoryName;
            this.price = price;
        }

        public static CategoryItem create(String categoryName, long price) {
            // T
            return new CategoryItem(categoryName, price);
        }
    }
}
