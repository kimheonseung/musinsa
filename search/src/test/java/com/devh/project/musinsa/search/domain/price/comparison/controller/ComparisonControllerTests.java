package com.devh.project.musinsa.search.domain.price.comparison.controller;

import com.devh.project.musinsa.search.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.search.domain.common.exception.SearchException;
import com.devh.project.musinsa.search.domain.price.comparison.dto.ComparisonPriceItemByCategoryResponse;
import com.devh.project.musinsa.search.domain.price.comparison.entity.ComparisonPriceItemByCategory;
import com.devh.project.musinsa.search.domain.price.comparison.service.ComparisonPriceItemByCategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DecimalFormat;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComparisonPriceController.class)
public class ComparisonControllerTests {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ComparisonPriceItemByCategoryService comparisonPriceItemByCategoryService;

    @Nested
    @DisplayName("카테고리명으로 최저, 최고 가격 브랜드와 상품 가격 조회")
    class 카테고리명으로_최저_최고_가격_브랜드와_상품_가격_조회 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("최저가 동일한 상품이 하나인 경우")
            void 최저가_동일한_상품이_하나인_경우() throws Exception {
                // given
                final DecimalFormat decimalFormat = new DecimalFormat("###,###");
                final String givenCategoryName = "상의";
                final String givenLowestBrandName = "H";
                final String givenHighestBrandName = "E";
                final long givenLowestPrice = 5_000L;
                final long givenHighestPrice = 5_400L;
                final String givenStrLowestPrice = decimalFormat.format(givenLowestPrice);
                final String givenStrHighestPrice = decimalFormat.format(givenHighestPrice);
                final ComparisonPriceItemByCategory comparisonPriceItemByCategory = ComparisonPriceItemByCategory.create(
                        givenCategoryName,
                        Set.of(ComparisonPriceItemByCategory.BrandItem.create(givenLowestBrandName, givenLowestPrice)),
                        Set.of(ComparisonPriceItemByCategory.BrandItem.create(givenHighestBrandName, givenHighestPrice))
                );
                final ComparisonPriceItemByCategoryResponse response = ComparisonPriceItemByCategoryResponse.create(comparisonPriceItemByCategory);
                given(comparisonPriceItemByCategoryService.getByCategory(givenCategoryName)).willReturn(response);
                // when & then
                mockMvc.perform(get("/search/api/v1/price/comparison/category?category="+givenCategoryName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.카테고리").value(givenCategoryName))
                        .andExpect(jsonPath("$.최저가.[0].브랜드").value(givenLowestBrandName))
                        .andExpect(jsonPath("$.최저가.[0].가격").value(givenStrLowestPrice))
                        .andExpect(jsonPath("$.최고가.[0].브랜드").value(givenHighestBrandName))
                        .andExpect(jsonPath("$.최고가.[0].가격").value(givenStrHighestPrice))
                        .andDo(print());
            }

            @Test
            @DisplayName("최저가 동일한 상품이 여러개인 경우")
            void 최저가_동일한_상품이_여러개인_경우() throws Exception {
                // given
                final DecimalFormat decimalFormat = new DecimalFormat("###,###");
                final String givenCategoryName = "상의";
                final String givenLowestBrandName1 = "A";
                final String givenLowestBrandName2 = "H";
                final String givenHighestBrandName = "E";
                final long givenLowestPrice = 5_000L;
                final long givenHighestPrice = 5_400L;
                final String givenStrLowestPrice = decimalFormat.format(givenLowestPrice);
                final String givenStrHighestPrice = decimalFormat.format(givenHighestPrice);
                final ComparisonPriceItemByCategory comparisonPriceItemByCategory = ComparisonPriceItemByCategory.create(
                        givenCategoryName,
                        Set.of(ComparisonPriceItemByCategory.BrandItem.create(givenLowestBrandName1, givenLowestPrice),
                               ComparisonPriceItemByCategory.BrandItem.create(givenLowestBrandName2, givenLowestPrice)),
                        Set.of(ComparisonPriceItemByCategory.BrandItem.create(givenHighestBrandName, givenHighestPrice))
                );
                final ComparisonPriceItemByCategoryResponse response = ComparisonPriceItemByCategoryResponse.create(comparisonPriceItemByCategory);
                given(comparisonPriceItemByCategoryService.getByCategory(givenCategoryName)).willReturn(response);
                // when & then
                mockMvc.perform(get("/search/api/v1/price/comparison/category?category="+givenCategoryName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.카테고리").value(givenCategoryName))
                        .andExpect(jsonPath("$.최저가.[0].가격").value(givenStrLowestPrice))
                        .andExpect(jsonPath("$.최저가.[0].브랜드").value(givenLowestBrandName1))
                        .andExpect(jsonPath("$.최저가.[1].가격").value(givenStrLowestPrice))
                        .andExpect(jsonPath("$.최저가.[1].브랜드").value(givenLowestBrandName2))
                        .andExpect(jsonPath("$.최고가.[0].브랜드").value(givenHighestBrandName))
                        .andExpect(jsonPath("$.최고가.[0].가격").value(givenStrHighestPrice))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("집계 정보가 존재하지 않음")
            void 집계_정보가_존재하지_않음() throws Exception {
                // given
                final String givenCategory = "집계정보가 존재하지 않는 카테고리";
                given(comparisonPriceItemByCategoryService.getByCategory(givenCategory)).willThrow(new SearchException(ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR));
                // when & then
                mockMvc.perform(get("/search/api/v1/price/comparison/category?category="+givenCategory)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR.getMessage()))
                        .andDo(print());
            }
        }
    }
}
