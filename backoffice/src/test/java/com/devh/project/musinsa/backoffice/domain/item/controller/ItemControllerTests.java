package com.devh.project.musinsa.backoffice.domain.item.controller;

import com.devh.project.musinsa.backoffice.domain.common.exception.BrandException;
import com.devh.project.musinsa.backoffice.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.backoffice.domain.common.exception.ItemException;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemAddRequest;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemDTO;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemDeleteRequest;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemUpdateRequest;
import com.devh.project.musinsa.backoffice.domain.item.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTests {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ItemService itemService;

    @Nested
    @DisplayName("상품 추가")
    class 상품_추가 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("A 브랜드의 스니커즈 카테고리 상품 추가")
            void A_브랜드의_스니커즈_카테고리_상품_추가() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenBrandName = "A";
                final String givenCategoryName = "스니커즈";
                final long givenPrice = 5_000L;
                final String givenItemName = "A-스니커즈";
                final ItemAddRequest givenItemAddRequest = new ItemAddRequest();
                Field brandNameField = givenItemAddRequest.getClass().getDeclaredField("brandName");
                Field categoryNameField = givenItemAddRequest.getClass().getDeclaredField("categoryName");
                Field priceField = givenItemAddRequest.getClass().getDeclaredField("price");
                Field itemNameField = givenItemAddRequest.getClass().getDeclaredField("itemName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemAddRequest, givenBrandName);
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemAddRequest, givenCategoryName);
                priceField.setAccessible(true);
                priceField.set(givenItemAddRequest, givenPrice);
                itemNameField.setAccessible(true);
                itemNameField.set(givenItemAddRequest, givenItemName);
                given(itemService.add(any(ItemAddRequest.class))).willReturn(ItemDTO.create(givenItemId,
                                                                                            givenItemName,
                                                                                            givenCategoryName,
                                                                                            givenBrandName,
                                                                                            givenPrice));
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.data.[0].id").value(givenItemId))
                        .andExpect(jsonPath("$.data.[0].name").value(givenItemName))
                        .andExpect(jsonPath("$.data.[0].category").value(givenCategoryName))
                        .andExpect(jsonPath("$.data.[0].brand").value(givenBrandName))
                        .andExpect(jsonPath("$.data.[0].price").value(givenPrice))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("유효하지 않은 브랜드명 요청값")
            void 유효하지_않은_브랜드명_요청값() throws Exception {
                // given
                final String givenBrandName = "";
                final String givenCategory = "스니커즈";
                final long givenPrice = 5_000L;
                final String givenItemName = "A-스니커즈";
                final ItemAddRequest givenItemAddRequest = new ItemAddRequest();
                Field brandNameField = givenItemAddRequest.getClass().getDeclaredField("brandName");
                Field categoryNameField = givenItemAddRequest.getClass().getDeclaredField("categoryName");
                Field priceField = givenItemAddRequest.getClass().getDeclaredField("price");
                Field itemNameField = givenItemAddRequest.getClass().getDeclaredField("itemName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemAddRequest, givenBrandName);
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemAddRequest, givenCategory);
                priceField.setAccessible(true);
                priceField.set(givenItemAddRequest, givenPrice);
                itemNameField.setAccessible(true);
                itemNameField.set(givenItemAddRequest, givenItemName);
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.REQUEST_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value("brand 명이 비어있습니다."))
                        .andDo(print());
            }

            @Test
            @DisplayName("유효하지 않은 카테고리명 요청값")
            void 유효하지_않은_카테고리명_요청값() throws Exception {
                // given
                final String givenBrandName = "A";
                final String givenCategory = "";
                final long givenPrice = 5_000L;
                final String givenItemName = "A-스니커즈";
                final ItemAddRequest givenItemAddRequest = new ItemAddRequest();
                Field brandNameField = givenItemAddRequest.getClass().getDeclaredField("brandName");
                Field categoryNameField = givenItemAddRequest.getClass().getDeclaredField("categoryName");
                Field priceField = givenItemAddRequest.getClass().getDeclaredField("price");
                Field itemNameField = givenItemAddRequest.getClass().getDeclaredField("itemName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemAddRequest, givenBrandName);
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemAddRequest, givenCategory);
                priceField.setAccessible(true);
                priceField.set(givenItemAddRequest, givenPrice);
                itemNameField.setAccessible(true);
                itemNameField.set(givenItemAddRequest, givenItemName);
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.REQUEST_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value("category 명이 비어있습니다."))
                        .andDo(print());
            }

            @Test
            @DisplayName("유효하지 않은 상품명 요청값")
            void 유효하지_않은_상품명_요청값() throws Exception {
                // given
                final String givenBrandName = "A";
                final String givenCategory = "스니커즈";
                final long givenPrice = 5_000L;
                final String givenItemName = "";
                final ItemAddRequest givenItemAddRequest = new ItemAddRequest();
                Field brandNameField = givenItemAddRequest.getClass().getDeclaredField("brandName");
                Field categoryNameField = givenItemAddRequest.getClass().getDeclaredField("categoryName");
                Field priceField = givenItemAddRequest.getClass().getDeclaredField("price");
                Field itemNameField = givenItemAddRequest.getClass().getDeclaredField("itemName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemAddRequest, givenBrandName);
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemAddRequest, givenCategory);
                priceField.setAccessible(true);
                priceField.set(givenItemAddRequest, givenPrice);
                itemNameField.setAccessible(true);
                itemNameField.set(givenItemAddRequest, givenItemName);
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.REQUEST_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value("상품 명이 비어있습니다."))
                        .andDo(print());
            }

            @Test
            @DisplayName("유효하지 않은 가격 요청값")
            void 유효하지_않은_가격_요청값() throws Exception {
                // given
                final String givenBrandName = "A";
                final String givenCategory = "스니커즈";
                final long givenPrice = -1L;
                final String givenItemName = "A-스니커즈";
                final ItemAddRequest givenItemAddRequest = new ItemAddRequest();
                Field brandNameField = givenItemAddRequest.getClass().getDeclaredField("brandName");
                Field categoryNameField = givenItemAddRequest.getClass().getDeclaredField("categoryName");
                Field priceField = givenItemAddRequest.getClass().getDeclaredField("price");
                Field itemNameField = givenItemAddRequest.getClass().getDeclaredField("itemName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemAddRequest, givenBrandName);
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemAddRequest, givenCategory);
                priceField.setAccessible(true);
                priceField.set(givenItemAddRequest, givenPrice);
                itemNameField.setAccessible(true);
                itemNameField.set(givenItemAddRequest, givenItemName);
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/item")
                        .content(objectMapper.writeValueAsString(givenItemAddRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.REQUEST_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value("잘못된 가격입니다."))
                        .andDo(print());
            }

            @Test
            @DisplayName("존재하지 않는 브랜드 입력")
            void 존재하지_않는_브랜드_입력() throws Exception {
                // given
                final String givenBrandName = "존재하지 않는 브랜드";
                final String givenCategory = "스니커즈";
                final long givenPrice = 999L;
                final String givenItemName = "A-스니커즈";
                final ItemAddRequest givenItemAddRequest = new ItemAddRequest();
                Field brandNameField = givenItemAddRequest.getClass().getDeclaredField("brandName");
                Field categoryNameField = givenItemAddRequest.getClass().getDeclaredField("categoryName");
                Field priceField = givenItemAddRequest.getClass().getDeclaredField("price");
                Field itemNameField = givenItemAddRequest.getClass().getDeclaredField("itemName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemAddRequest, givenBrandName);
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemAddRequest, givenCategory);
                priceField.setAccessible(true);
                priceField.set(givenItemAddRequest, givenPrice);
                itemNameField.setAccessible(true);
                itemNameField.set(givenItemAddRequest, givenItemName);
                given(itemService.add(any(ItemAddRequest.class))).willThrow(new BrandException(ErrorCode.BRAND_NOT_FOUND_ERROR));
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.BRAND_NOT_FOUND_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_NOT_FOUND_ERROR.getMessage()))
                        .andDo(print());
            }

            @Test
            @DisplayName("존재하지 않는 카테고리 입력")
            void 존재하지_않는_카테고리_입력() throws Exception {
                // given
                final String givenBrandName = "A";
                final String givenCategory = "존재하지 않는 카테고리";
                final long givenPrice = 999L;
                final String givenItemName = "A-스니커즈";
                final ItemAddRequest givenItemAddRequest = new ItemAddRequest();
                Field brandNameField = givenItemAddRequest.getClass().getDeclaredField("brandName");
                Field categoryNameField = givenItemAddRequest.getClass().getDeclaredField("categoryName");
                Field priceField = givenItemAddRequest.getClass().getDeclaredField("price");
                Field itemNameField = givenItemAddRequest.getClass().getDeclaredField("itemName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemAddRequest, givenBrandName);
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemAddRequest, givenCategory);
                priceField.setAccessible(true);
                priceField.set(givenItemAddRequest, givenPrice);
                itemNameField.setAccessible(true);
                itemNameField.set(givenItemAddRequest, givenItemName);
                given(itemService.add(any(ItemAddRequest.class))).willThrow(new BrandException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.CATEGORY_NOT_FOUND_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.CATEGORY_NOT_FOUND_ERROR.getMessage()))
                        .andDo(print());
            }

            @Test
            @DisplayName("중복 상품 입력 (상품명 + 카테고리명 + 브랜드명)")
            void 중복_상품_입력() throws Exception {
                // given
                final String givenBrandName = "중복-A";
                final String givenCategory = "중복-스니커즈";
                final long givenPrice = 999L;
                final String givenItemName = "중복-A-스니커즈";
                final ItemAddRequest givenItemAddRequest = new ItemAddRequest();
                Field brandNameField = givenItemAddRequest.getClass().getDeclaredField("brandName");
                Field categoryNameField = givenItemAddRequest.getClass().getDeclaredField("categoryName");
                Field priceField = givenItemAddRequest.getClass().getDeclaredField("price");
                Field itemNameField = givenItemAddRequest.getClass().getDeclaredField("itemName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemAddRequest, givenBrandName);
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemAddRequest, givenCategory);
                priceField.setAccessible(true);
                priceField.set(givenItemAddRequest, givenPrice);
                itemNameField.setAccessible(true);
                itemNameField.set(givenItemAddRequest, givenItemName);
                given(itemService.add(any(ItemAddRequest.class))).willThrow(new BrandException(ErrorCode.ITEM_DUPLICATE_ERROR));
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.ITEM_DUPLICATE_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.ITEM_DUPLICATE_ERROR.getMessage()))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("상품 수정")
    class 상품_수정 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("브랜드 변경")
            void 브랜드_변경() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenItemName = "기존 상품명";
                final String givenCategoryName = "기존 카테고리명";
                final String givenBrandName = "새로운 브랜드명";
                final Long givenPrice = 50_000L;
                final ItemUpdateRequest givenItemUpdateRequest = new ItemUpdateRequest();
                Field brandNameField = givenItemUpdateRequest.getClass().getDeclaredField("brandName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemUpdateRequest, givenBrandName);
                Field itemIdField = givenItemUpdateRequest.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItemUpdateRequest, givenItemId);
                given(itemService.update(any(ItemUpdateRequest.class))).willReturn(ItemDTO.create(givenItemId,
                                                                                                  givenItemName,
                                                                                                  givenCategoryName,
                                                                                                  givenBrandName,
                                                                                                  givenPrice));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.data.[0].id").value(givenItemId))
                        .andExpect(jsonPath("$.data.[0].name").value(givenItemName))
                        .andExpect(jsonPath("$.data.[0].category").value(givenCategoryName))
                        .andExpect(jsonPath("$.data.[0].brand").value(givenBrandName))
                        .andExpect(jsonPath("$.data.[0].price").value(givenPrice))
                        .andDo(print());
            }

            @Test
            @DisplayName("카테고리 변경")
            void 카테고리_변경() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenItemName = "기존 상품명";
                final String givenCategoryName = "새로운 카테고리명";
                final String givenBrandName = "기존 브랜드명";
                final Long givenPrice = 10_000L;
                final ItemUpdateRequest givenItemUpdateRequest = new ItemUpdateRequest();
                Field categoryNameField = givenItemUpdateRequest.getClass().getDeclaredField("categoryName");
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemUpdateRequest, givenCategoryName);
                Field itemIdField = givenItemUpdateRequest.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItemUpdateRequest, givenItemId);
                given(itemService.update(any(ItemUpdateRequest.class))).willReturn(ItemDTO.create(givenItemId,
                                                                                                  givenItemName,
                                                                                                  givenCategoryName,
                                                                                                  givenBrandName,
                                                                                                  givenPrice));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.data.[0].id").value(givenItemId))
                        .andExpect(jsonPath("$.data.[0].name").value(givenItemName))
                        .andExpect(jsonPath("$.data.[0].category").value(givenCategoryName))
                        .andExpect(jsonPath("$.data.[0].brand").value(givenBrandName))
                        .andExpect(jsonPath("$.data.[0].price").value(givenPrice))
                        .andDo(print());
            }

            @Test
            @DisplayName("가격 변경")
            void 가격_변경() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenItemName = "기존 상품명";
                final String givenCategoryName = "기존 카테고리명";
                final String givenBrandName = "기존 브랜드명";
                final long givenPrice = 11_111L;
                final ItemUpdateRequest givenItemUpdateRequest = new ItemUpdateRequest();
                Field priceField = givenItemUpdateRequest.getClass().getDeclaredField("price");
                priceField.setAccessible(true);
                priceField.set(givenItemUpdateRequest, givenPrice);
                Field itemIdField = givenItemUpdateRequest.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItemUpdateRequest, givenItemId);
                given(itemService.update(any(ItemUpdateRequest.class))).willReturn(ItemDTO.create(givenItemId,
                                                                                                  givenItemName,
                                                                                                  givenCategoryName,
                                                                                                  givenBrandName,
                                                                                                  givenPrice));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.data.[0].id").value(givenItemId))
                        .andExpect(jsonPath("$.data.[0].name").value(givenItemName))
                        .andExpect(jsonPath("$.data.[0].category").value(givenCategoryName))
                        .andExpect(jsonPath("$.data.[0].brand").value(givenBrandName))
                        .andExpect(jsonPath("$.data.[0].price").value(givenPrice))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("존재하지 않는 상품 id 입력")
            void 존재하지_않는_상품_id() throws Exception {
                // given
                final long givenItemId = 999L;
                final ItemUpdateRequest givenItemUpdateRequest = new ItemUpdateRequest();
                Field itemIdField = givenItemUpdateRequest.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItemUpdateRequest, givenItemId);
                given(itemService.update(any(ItemUpdateRequest.class))).willThrow(new ItemException(ErrorCode.ITEM_NOT_FOUND_ERROR));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.ITEM_NOT_FOUND_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.ITEM_NOT_FOUND_ERROR.getMessage()))
                        .andDo(print());
            }

            @Test
            @DisplayName("변경할 값을 입력하지 않음")
            void 변경할_값이_없음() throws Exception {
                // given
                final long givenItemId = 999L;
                final ItemUpdateRequest givenItemUpdateRequest = new ItemUpdateRequest();
                Field itemIdField = givenItemUpdateRequest.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItemUpdateRequest, givenItemId);
                given(itemService.update(any(ItemUpdateRequest.class))).willThrow(new ItemException(ErrorCode.ITEM_UPDATE_ERROR));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.ITEM_UPDATE_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.ITEM_UPDATE_ERROR.getMessage()))
                        .andDo(print());
            }

            @Test
            @DisplayName("존재하지 않는 브랜드명 입력")
            void 존재하지_않는_브랜드명_입력() throws Exception {
                // given
                final long givenItemId = 999L;
                final String givenBrandName = "존재하지 않는 브랜드명";
                final ItemUpdateRequest givenItemUpdateRequest = new ItemUpdateRequest();
                Field itemIdField = givenItemUpdateRequest.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItemUpdateRequest, givenItemId);
                Field brandNameField = givenItemUpdateRequest.getClass().getDeclaredField("brandName");
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemUpdateRequest, givenBrandName);
                given(itemService.update(any(ItemUpdateRequest.class))).willThrow(new BrandException(ErrorCode.BRAND_NOT_FOUND_ERROR));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.BRAND_NOT_FOUND_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_NOT_FOUND_ERROR.getMessage()))
                        .andDo(print());
            }

            @Test
            @DisplayName("변경된 상품 정보가 이미 존재하는 상품")
            void 변경된_상품_정보가_이미_존재하는_상품() throws Exception {
                // given
                final long givenItemId = 999L;
                final String givenBrandName = "중복-A";
                final String givenCategory = "중복-스니커즈";
                final long givenPrice = 11_111L;
                final String givenItemName = "중복-A-스니커즈";
                final ItemUpdateRequest givenItemUpdateRequest = new ItemUpdateRequest();
                Field itemIdField = givenItemUpdateRequest.getClass().getDeclaredField("id");
                Field brandNameField = givenItemUpdateRequest.getClass().getDeclaredField("brandName");
                Field categoryNameField = givenItemUpdateRequest.getClass().getDeclaredField("categoryName");
                Field priceField = givenItemUpdateRequest.getClass().getDeclaredField("price");
                Field itemNameField = givenItemUpdateRequest.getClass().getDeclaredField("itemName");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItemUpdateRequest, givenItemId);
                brandNameField.setAccessible(true);
                brandNameField.set(givenItemUpdateRequest, givenBrandName);
                categoryNameField.setAccessible(true);
                categoryNameField.set(givenItemUpdateRequest, givenCategory);
                priceField.setAccessible(true);
                priceField.set(givenItemUpdateRequest, givenPrice);
                itemNameField.setAccessible(true);
                itemNameField.set(givenItemUpdateRequest, givenItemName);
                given(itemService.update(any(ItemUpdateRequest.class))).willThrow(new ItemException(ErrorCode.ITEM_DUPLICATE_ERROR));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(givenItemUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.ITEM_DUPLICATE_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.ITEM_DUPLICATE_ERROR.getMessage()))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("상품 삭제")
    class 상품_삭제 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("id가 1인 상품 식제")
            void id가_1인_상품_삭제() throws Exception {
                // given
                final Long givenItemId = 1L;
                final String givenItemName = "기존 상품명";
                final String givenCategoryName = "기존 카테고리명";
                final String givenBrandName = "기존 브랜드명";
                final Long givenPrice = 10_000L;
                final ItemDeleteRequest itemDeleteRequest = new ItemDeleteRequest();
                Field itemIdField = itemDeleteRequest.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(itemDeleteRequest, givenItemId);
                BDDMockito.given(itemService.delete(any(ItemDeleteRequest.class))).willReturn(ItemDTO.create(givenItemId,
                                                                                                             givenItemName,
                                                                                                             givenCategoryName,
                                                                                                             givenBrandName,
                                                                                                             givenPrice));
                // when & then
                mockMvc.perform(delete("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(itemDeleteRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.data.[0].id").value(givenItemId))
                        .andExpect(jsonPath("$.data.[0].name").value(givenItemName))
                        .andExpect(jsonPath("$.data.[0].category").value(givenCategoryName))
                        .andExpect(jsonPath("$.data.[0].brand").value(givenBrandName))
                        .andExpect(jsonPath("$.data.[0].price").value(givenPrice))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("유효하지 않은 상품 id 입력")
            void 유효하지_않은_상품_id_입력() throws Exception {
                // given
                final ItemDeleteRequest itemDeleteRequest = new ItemDeleteRequest();
                Field itemIdField = itemDeleteRequest.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(itemDeleteRequest, -1L);
                // when & then
                mockMvc.perform(delete("/backoffice/api/v1/item")
                                .content(objectMapper.writeValueAsString(itemDeleteRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.REQUEST_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value("삭제할 상품 id가 잘못되었습니다."))
                        .andDo(print());
            }
        }
    }
}
