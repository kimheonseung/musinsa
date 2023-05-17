package com.devh.project.musinsa.search.domain.price.comparison.service;

import com.devh.project.musinsa.search.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.search.domain.common.exception.SearchException;
import com.devh.project.musinsa.search.domain.price.comparison.dto.ComparisonPriceItemByCategoryResponse;
import com.devh.project.musinsa.search.domain.price.comparison.entity.ComparisonPriceItemByCategory;
import com.devh.project.musinsa.search.domain.price.comparison.repository.ComparisonPriceItemByCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ComparisonPriceItemByCategoryServiceTests {
    @InjectMocks
    ComparisonPriceItemByCategoryServiceImpl comparisonPriceItemByCategoryService;
    @Mock
    ComparisonPriceItemByCategoryRepository comparisonPriceItemByCategoryRepository;
    @Mock
    ComparisonPriceItemByCategory comparisonPriceItemByCategory;

    @Nested
    @DisplayName("카테고리별 최저가와 최고가 및 브랜드 조회")
    class 카테고리별_최저가_최고가_및_브랜드_조회 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("카테고리명이 아우터인 데이터 조회 성공")
            void 카테고리명이_A인_데이터_조회_성공() {
                // given
                final DecimalFormat decimalFormat = new DecimalFormat("###,###");
                final String givenCategoryName = "아우터";
                final String givenLowestPriceBrandName = "A";
                final long givenLowestPrice = 5_000;
                final String givenStrLowestPrice = decimalFormat.format(givenLowestPrice);
                final String givenHighestPriceBrandName = "H";
                final long givenHighestPrice = 5_500;
                final String givenStrHighestPrice = decimalFormat.format(givenHighestPrice);
                final ComparisonPriceItemByCategory.BrandItem givenLowestBrandItem = ComparisonPriceItemByCategory.BrandItem.create(givenLowestPriceBrandName, givenLowestPrice);
                final ComparisonPriceItemByCategory.BrandItem givenHighestBrandItem = ComparisonPriceItemByCategory.BrandItem.create(givenHighestPriceBrandName, givenHighestPrice);
                given(comparisonPriceItemByCategory.getCategoryName()).willReturn(givenCategoryName);
                given(comparisonPriceItemByCategory.getLowestPriceBrandItems()).willReturn(Set.of(givenLowestBrandItem));
                given(comparisonPriceItemByCategory.getHighestPriceBrandItems()).willReturn(Set.of(givenHighestBrandItem));
                given(comparisonPriceItemByCategoryRepository.existsById(givenCategoryName)).willReturn(true);
                given(comparisonPriceItemByCategoryRepository.findById(givenCategoryName)).willReturn(Optional.of(comparisonPriceItemByCategory));
                // when
                ComparisonPriceItemByCategoryResponse comparisonPriceItemByCategoryResponse = comparisonPriceItemByCategoryService.getByCategory(givenCategoryName);
                // then
                assertAll(
                        () -> assertEquals(givenCategoryName, comparisonPriceItemByCategoryResponse.get카테고리()),
                        () -> assertEquals(givenLowestPriceBrandName, comparisonPriceItemByCategoryResponse.get최저가().get(0).get브랜드()),
                        () -> assertEquals(givenStrLowestPrice, comparisonPriceItemByCategoryResponse.get최저가().get(0).get가격()),
                        () -> assertEquals(givenHighestPriceBrandName, comparisonPriceItemByCategoryResponse.get최고가().get(0).get브랜드()),
                        () -> assertEquals(givenStrHighestPrice, comparisonPriceItemByCategoryResponse.get최고가().get(0).get가격())
                );
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("조회할 카테고리에 대한 집계데이터가 존재하지 않음")
            void 조회할_카테고리에_대한_집계데이터가_존재하지_않음() {
                // given
                final String givenCategoryName = "존재하지 않는 카테고리명";
                given(comparisonPriceItemByCategoryRepository.existsById(givenCategoryName)).willReturn(false);
                // when & then
                assertThrows(SearchException.class,
                             () -> comparisonPriceItemByCategoryService.getByCategory(givenCategoryName),
                             ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR.getMessage());
            }
        }
    }
}
