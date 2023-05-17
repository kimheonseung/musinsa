package com.devh.project.musinsa.search.domain.price.lowest.controller;

import com.devh.project.musinsa.search.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.search.domain.common.exception.SearchException;
import com.devh.project.musinsa.search.domain.price.lowest.dto.LowestPriceItemByBrandResponse;
import com.devh.project.musinsa.search.domain.price.lowest.dto.LowestPriceItemByCategoryResponse;
import com.devh.project.musinsa.search.domain.price.lowest.entity.LowestPriceItemByBrand;
import com.devh.project.musinsa.search.domain.price.lowest.entity.LowestPriceItemByCategory;
import com.devh.project.musinsa.search.domain.price.lowest.service.LowestPriceItemByBrandService;
import com.devh.project.musinsa.search.domain.price.lowest.service.LowestPriceItemByCategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DecimalFormat;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LowestPriceController.class)
public class LowestPriceControllerTests {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;
    @MockBean
    LowestPriceItemByBrandService lowestPriceItemByBrandService;
    @MockBean
    LowestPriceItemByCategoryService lowestPriceItemByCategoryService;

    @Nested
    @DisplayName("카테고리별 최저가 상품 목록 및 총 가격 조회")
    class 카테고리별_최저가_상품_목록_및_총_가격 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("카테고리별 최저가 상품 목록")
            void 카테고리별_최저가_상품_목록() throws Exception {
                // given
                final DecimalFormat decimalFormat = new DecimalFormat("###,###");
                final String givenCategory1 = "상의";
                final String givenCategory2 = "바지";
                final String givenCategory3 = "아우터";
                final String givenBrand1 = "A";
                final String givenBrand2 = "I";
                final String givenBrand3 = "G";
                final long givenPrice1 = 5_000L;
                final long givenPrice2 = 4_000L;
                final long givenPrice3 = 10_000L;
                final String givenStrPrice1 = decimalFormat.format(givenPrice1);
                final String givenStrPrice2 = decimalFormat.format(givenPrice2);
                final String givenStrPrice3 = decimalFormat.format(givenPrice3);
                final String givenTotalPrice = decimalFormat.format(givenPrice1+givenPrice2+givenPrice3);
                final LowestPriceItemByCategory lowestPriceItemByCategory1 =
                        LowestPriceItemByCategory.create(givenCategory1, givenBrand1, givenPrice1);
                final LowestPriceItemByCategory lowestPriceItemByCategory2 =
                        LowestPriceItemByCategory.create(givenCategory2, givenBrand2, givenPrice2);
                final LowestPriceItemByCategory lowestPriceItemByCategory3 =
                        LowestPriceItemByCategory.create(givenCategory3, givenBrand3, givenPrice3);
                final LowestPriceItemByCategoryResponse givenResponse = LowestPriceItemByCategoryResponse.create(
                        List.of(lowestPriceItemByCategory1, lowestPriceItemByCategory2, lowestPriceItemByCategory3)
                );
                given(lowestPriceItemByCategoryService.get()).willReturn(givenResponse);
                // when & then
                mockMvc.perform(get("/search/api/v1/price/lowest/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.totalPrice").value(givenTotalPrice))
                        .andExpect(jsonPath("$.items.[0].category").value(givenCategory1))
                        .andExpect(jsonPath("$.items.[0].brand").value(givenBrand1))
                        .andExpect(jsonPath("$.items.[0].price").value(givenStrPrice1))
                        .andExpect(jsonPath("$.items.[1].category").value(givenCategory2))
                        .andExpect(jsonPath("$.items.[1].brand").value(givenBrand2))
                        .andExpect(jsonPath("$.items.[1].price").value(givenStrPrice2))
                        .andExpect(jsonPath("$.items.[2].category").value(givenCategory3))
                        .andExpect(jsonPath("$.items.[2].brand").value(givenBrand3))
                        .andExpect(jsonPath("$.items.[2].price").value(givenStrPrice3))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("집계 데이터가 존재하지 않음")
            void 집계_데이터가_존재하지_않음() throws Exception {
                // given
                BDDMockito.given(lowestPriceItemByCategoryService.get()).willThrow(new SearchException(ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR));
                // when & then
                mockMvc.perform(get("/search/api/v1/price/lowest/category")
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

    @Nested
    @DisplayName("한 브랜드 구매 시 최저가 정보")
    class 한_브랜드_구매_시_최저가_정보 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("브랜드가 A인 최저가 구매 정보가 반환")
            void 브랜드가_A인_최저가_구매_정보가_반환() throws Exception {
                // given
                final DecimalFormat decimalFormat = new DecimalFormat("###,###");
                final String givenBrandName = "A";
                final String givenCategory1 = "상의";
                final String givenCategory2 = "바지";
                final String givenCategory3 = "아우터";
                final long givenPrice1 = 5_000L;
                final long givenPrice2 = 4_000L;
                final long givenPrice3 = 15_000L;
                final String givenStrPrice1 = decimalFormat.format(givenPrice1);
                final String givenStrPrice2 = decimalFormat.format(givenPrice2);
                final String givenStrPrice3 = decimalFormat.format(givenPrice3);
                final String totalStrPrice = decimalFormat.format(givenPrice1+givenPrice2+givenPrice3);
                List<LowestPriceItemByBrand.CategoryItem> categoryItems = List.of(
                        LowestPriceItemByBrand.CategoryItem.create(givenCategory1, givenPrice1),
                        LowestPriceItemByBrand.CategoryItem.create(givenCategory2, givenPrice2),
                        LowestPriceItemByBrand.CategoryItem.create(givenCategory3, givenPrice3)
                );
                final LowestPriceItemByBrand givenItemByBrand = LowestPriceItemByBrand.create(
                        givenBrandName, categoryItems, givenPrice1+givenPrice2+givenPrice3
                );
                final LowestPriceItemByBrandResponse response = LowestPriceItemByBrandResponse.create(givenItemByBrand);
                given(lowestPriceItemByBrandService.get()).willReturn(response);
                // when & then
                mockMvc.perform(get("/search/api/v1/price/lowest/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.최저가.브랜드").value(givenBrandName))
                        .andExpect(jsonPath("$.최저가.총액").value(totalStrPrice))
                        .andExpect(jsonPath("$.최저가.카테고리.[0].카테고리").value(givenCategory1))
                        .andExpect(jsonPath("$.최저가.카테고리.[0].가격").value(givenStrPrice1))
                        .andExpect(jsonPath("$.최저가.카테고리.[1].카테고리").value(givenCategory2))
                        .andExpect(jsonPath("$.최저가.카테고리.[1].가격").value(givenStrPrice2))
                        .andExpect(jsonPath("$.최저가.카테고리.[2].카테고리").value(givenCategory3))
                        .andExpect(jsonPath("$.최저가.카테고리.[2].가격").value(givenStrPrice3))
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
                given(lowestPriceItemByBrandService.get()).willThrow(new SearchException(ErrorCode.SEARCH_PRICE_NOT_FOUND_ERROR));
                // when & then
                mockMvc.perform(get("/search/api/v1/price/lowest/brand")
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
