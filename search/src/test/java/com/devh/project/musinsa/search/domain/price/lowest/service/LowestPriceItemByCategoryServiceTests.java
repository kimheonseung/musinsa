package com.devh.project.musinsa.search.domain.price.lowest.service;

import com.devh.project.musinsa.search.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.search.domain.common.exception.SearchException;
import com.devh.project.musinsa.search.domain.price.lowest.dto.LowestPriceItemByCategoryResponse;
import com.devh.project.musinsa.search.domain.price.lowest.entity.LowestPriceItemByCategory;
import com.devh.project.musinsa.search.domain.price.lowest.repository.LowestPriceItemByCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LowestPriceItemByCategoryServiceTests {
    @InjectMocks
    LowestPriceItemByCategoryServiceImpl lowestPriceItemByCategoryService;
    @Mock
    LowestPriceItemByCategoryRepository lowestPriceItemByCategoryRepository;
    @Mock
    LowestPriceItemByCategory lowestPriceItemByCategory1;
    @Mock
    LowestPriceItemByCategory lowestPriceItemByCategory2;
    @Mock
    LowestPriceItemByCategory lowestPriceItemByCategory3;
    @Mock
    LowestPriceItemByCategory lowestPriceItemByCategory4;

    @Nested
    @DisplayName("카테고리별 최저가 브랜드와 상품 가격 조회")
    class 카테고리별_최저가_브랜드와_상품_가격_조회 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("카테고리별 최저가 상품 정보 조회")
            void 카테고리별_최저가_상품_정보_조회() {
                // given
                final DecimalFormat decimalFormat = new DecimalFormat("###,###");
                final String givenCategoryName1 = "상의";
                final String givenBrandName1 = "A";
                final Long givenPrice1 = 5_500L;
                final String givenStrPrice1 = decimalFormat.format(givenPrice1);
                final String givenCategoryName2 = "아우터";
                final String givenBrandName2 = "I";
                final Long givenPrice2 = 11_400L;
                final String givenStrPrice2 = decimalFormat.format(givenPrice2);
                final String givenCategoryName3 = "스니커즈";
                final String givenBrandName3 = "G";
                final Long givenPrice3 = 9_000L;
                final String givenStrPrice3 = decimalFormat.format(givenPrice3);
                final String givenCategoryName4 = "바지";
                final String givenBrandName4 = "A";
                final Long givenPrice4 = 3_000L;
                final String givenStrPrice4 = decimalFormat.format(givenPrice4);
                final String givenStrTotalPrice = decimalFormat.format(givenPrice1+givenPrice2+givenPrice3+givenPrice4);
                final int givenSize = 4;
                given(lowestPriceItemByCategory1.getCategoryName()).willReturn(givenCategoryName1);
                given(lowestPriceItemByCategory1.getBrandName()).willReturn(givenBrandName1);
                given(lowestPriceItemByCategory1.getPrice()).willReturn(givenPrice1);
                given(lowestPriceItemByCategory2.getCategoryName()).willReturn(givenCategoryName2);
                given(lowestPriceItemByCategory2.getBrandName()).willReturn(givenBrandName2);
                given(lowestPriceItemByCategory2.getPrice()).willReturn(givenPrice2);
                given(lowestPriceItemByCategory3.getCategoryName()).willReturn(givenCategoryName3);
                given(lowestPriceItemByCategory3.getBrandName()).willReturn(givenBrandName3);
                given(lowestPriceItemByCategory3.getPrice()).willReturn(givenPrice3);
                given(lowestPriceItemByCategory4.getCategoryName()).willReturn(givenCategoryName4);
                given(lowestPriceItemByCategory4.getBrandName()).willReturn(givenBrandName4);
                given(lowestPriceItemByCategory4.getPrice()).willReturn(givenPrice4);
                given(lowestPriceItemByCategoryRepository.findAll()).willReturn(List.of(lowestPriceItemByCategory1,
                                                                                        lowestPriceItemByCategory2,
                                                                                        lowestPriceItemByCategory3,
                                                                                        lowestPriceItemByCategory4));
                // when
                LowestPriceItemByCategoryResponse LowestPriceItemByCategoryResponse = lowestPriceItemByCategoryService.get();
                // then
                assertAll(
                        () -> assertEquals(givenSize, LowestPriceItemByCategoryResponse.getItems().size()),
                        () -> assertEquals(givenCategoryName1, LowestPriceItemByCategoryResponse.getItems().get(0).getCategory()),
                        () -> assertEquals(givenBrandName1, LowestPriceItemByCategoryResponse.getItems().get(0).getBrand()),
                        () -> assertEquals(givenStrPrice1, LowestPriceItemByCategoryResponse.getItems().get(0).getPrice()),
                        () -> assertEquals(givenCategoryName2, LowestPriceItemByCategoryResponse.getItems().get(1).getCategory()),
                        () -> assertEquals(givenBrandName2, LowestPriceItemByCategoryResponse.getItems().get(1).getBrand()),
                        () -> assertEquals(givenStrPrice2, LowestPriceItemByCategoryResponse.getItems().get(1).getPrice()),
                        () -> assertEquals(givenCategoryName3, LowestPriceItemByCategoryResponse.getItems().get(2).getCategory()),
                        () -> assertEquals(givenBrandName3, LowestPriceItemByCategoryResponse.getItems().get(2).getBrand()),
                        () -> assertEquals(givenStrPrice3, LowestPriceItemByCategoryResponse.getItems().get(2).getPrice()),
                        () -> assertEquals(givenCategoryName4, LowestPriceItemByCategoryResponse.getItems().get(3).getCategory()),
                        () -> assertEquals(givenBrandName4, LowestPriceItemByCategoryResponse.getItems().get(3).getBrand()),
                        () -> assertEquals(givenStrPrice4, LowestPriceItemByCategoryResponse.getItems().get(3).getPrice()),
                        () -> assertEquals(givenStrTotalPrice, LowestPriceItemByCategoryResponse.getTotalPrice())
                );
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("조회된 데이터 없는 경우")
            void 조회된_데이터_없는_경우() {
                // given
                given(lowestPriceItemByCategoryRepository.findAll()).willReturn(Collections.emptyList());
                // when
                assertThrows(SearchException.class, () -> lowestPriceItemByCategoryService.get(), ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR.getMessage());
            }
        }
    }
}
