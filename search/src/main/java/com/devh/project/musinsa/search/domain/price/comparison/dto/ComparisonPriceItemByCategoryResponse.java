package com.devh.project.musinsa.search.domain.price.comparison.dto;

import com.devh.project.musinsa.search.domain.price.comparison.entity.ComparisonPriceItemByCategory;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ComparisonPriceItemByCategoryResponse {
    private String 카테고리;
    private List<BrandItemDTO> 최저가;
    private List<BrandItemDTO> 최고가;

    private ComparisonPriceItemByCategoryResponse(String categoryName, List<BrandItemDTO> lowestPriceItems, List<BrandItemDTO> highestPriceItems) {
        this.카테고리 = categoryName;
        this.최저가 = lowestPriceItems;
        this.최고가 = highestPriceItems;
    }

    public static ComparisonPriceItemByCategoryResponse create(ComparisonPriceItemByCategory comparisonPriceItemByCategory) {
        Set<ComparisonPriceItemByCategory.BrandItem> lowests = comparisonPriceItemByCategory.getLowestPriceBrandItems();
        Set<ComparisonPriceItemByCategory.BrandItem> highests = comparisonPriceItemByCategory.getHighestPriceBrandItems();
        List<BrandItemDTO> lowestItems = lowests.stream().map(l -> BrandItemDTO.create(l.getBrandName(), l.getPrice())).sorted((c1, c2) -> StringUtils.compare(c1.get브랜드(), c2.get브랜드())).collect(Collectors.toList());
        List<BrandItemDTO> highestItems = highests.stream().map(h -> BrandItemDTO.create(h.getBrandName(), h.getPrice())).sorted((c1, c2) -> StringUtils.compare(c1.get브랜드(), c2.get브랜드())).collect(Collectors.toList());
        return new ComparisonPriceItemByCategoryResponse(comparisonPriceItemByCategory.getCategoryName(),
                                                         lowestItems,
                                                         highestItems);
    }

    @Getter
    public static class BrandItemDTO {
        private final String 브랜드;
        private final String 가격;

        private BrandItemDTO(String brandName, String price) {
            this.브랜드 = brandName;
            this.가격 = price;
        }

        public static BrandItemDTO create(String brandName, long price) {
            return new BrandItemDTO(brandName, new DecimalFormat("###,###").format(price));
        }
    }
}
