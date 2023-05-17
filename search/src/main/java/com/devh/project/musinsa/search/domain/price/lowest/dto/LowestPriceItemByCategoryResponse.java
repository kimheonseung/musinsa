package com.devh.project.musinsa.search.domain.price.lowest.dto;

import com.devh.project.musinsa.search.domain.price.lowest.entity.LowestPriceItemByCategory;
import lombok.Getter;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LowestPriceItemByCategoryResponse {
    private List<LowestPriceItemByCategoryDTO> items;
    private String totalPrice;

    private LowestPriceItemByCategoryResponse(List<LowestPriceItemByCategory> list) {
        this.items = list.stream().map(LowestPriceItemByCategoryDTO::create).collect(Collectors.toList());
        this.totalPrice = new DecimalFormat("###,###").format(
                this.items.stream().mapToLong(l -> l.lPrice).sum());
    }

    public static LowestPriceItemByCategoryResponse create(List<LowestPriceItemByCategory> list) {
        return new LowestPriceItemByCategoryResponse(list);
    }

    public static class LowestPriceItemByCategoryDTO {
        private final String category;
        private final String brand;
        private final String price;
        private final long lPrice;

        private LowestPriceItemByCategoryDTO(String category, String brand, long price) {
            this.category = category;
            this.brand = brand;
            this.lPrice = price;
            this.price = new DecimalFormat("###,###").format(lPrice);
        }

        public static LowestPriceItemByCategoryDTO create(LowestPriceItemByCategory lowestPriceItemByCategory) {
            return new LowestPriceItemByCategoryDTO(lowestPriceItemByCategory.getCategoryName(),
                                                    lowestPriceItemByCategory.getBrandName(),
                                                    lowestPriceItemByCategory.getPrice());
        }

        public String getCategory() {
            return category;
        }

        public String getBrand() {
            return brand;
        }

        public String getPrice() {
            return this.price;
        }
    }
}
