package com.devh.project.musinsa.search.domain.price.lowest.dto;

import com.devh.project.musinsa.search.domain.price.lowest.entity.LowestPriceItemByBrand;
import lombok.Getter;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LowestPriceItemByBrandResponse {
    private LowestPriceItemByBrandDTO 최저가;

    private LowestPriceItemByBrandResponse(LowestPriceItemByBrandDTO lowestPriceItemByBrandDTO) {
        this.최저가 = lowestPriceItemByBrandDTO;
    }

    public static LowestPriceItemByBrandResponse create(LowestPriceItemByBrand lowestPriceItemByBrand) {
        return new LowestPriceItemByBrandResponse(
                LowestPriceItemByBrandDTO.create(lowestPriceItemByBrand.getBrandName(), lowestPriceItemByBrand.getCategoryItems()));
    }

    @Getter
    public static class LowestPriceItemByBrandDTO {
        private String 브랜드;
        private List<CategoryItemDTO> 카테고리;
        private String 총액;

        private LowestPriceItemByBrandDTO(String brandName, List<CategoryItemDTO> categoryItemDTOS) {
            this.브랜드 = brandName;
            this.카테고리 = categoryItemDTOS;
            this.총액 = new DecimalFormat("###,###").format(categoryItemDTOS.stream().mapToLong(dto -> dto.price).sum());
        }

        public static LowestPriceItemByBrandDTO create (String brandName,
                                                        List<LowestPriceItemByBrand.CategoryItem> categoryItems) {
            List<CategoryItemDTO> categoryItemDTOS = categoryItems.stream()
                    .map(categoryItem -> CategoryItemDTO.create(categoryItem.getCategoryName(), categoryItem.getPrice()))
                    .collect(Collectors.toList());
            return new LowestPriceItemByBrandDTO(brandName, categoryItemDTOS);
        }

    }

    private static class CategoryItemDTO {
        private String 카테고리;
        private String 가격;
        private long price;

        private CategoryItemDTO(String categoryName, long price) {
            this.카테고리 = categoryName;
            this.price = price;
            this.가격 = new DecimalFormat("###,###").format(this.price);
        }

        public static CategoryItemDTO create(String categoryName, long price) {
            return new CategoryItemDTO(categoryName, price);
        }

        public String get카테고리() {
            return this.카테고리;
        }

        public String get가격() {
            return this.가격;
        }
    }
}
