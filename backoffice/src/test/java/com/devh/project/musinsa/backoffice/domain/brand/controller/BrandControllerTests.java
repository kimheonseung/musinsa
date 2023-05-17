package com.devh.project.musinsa.backoffice.domain.brand.controller;

import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandAddRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDTO;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDeleteRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandUpdateRequest;
import com.devh.project.musinsa.backoffice.domain.brand.service.BrandService;
import com.devh.project.musinsa.backoffice.domain.common.exception.BrandException;
import com.devh.project.musinsa.backoffice.domain.common.exception.ErrorCode;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BrandController.class)
public class BrandControllerTests {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BrandService brandService;

    @Nested
    @DisplayName("브랜드 추가")
    class 브랜드_추가 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("이름이 X인 브랜드 추가")
            void 이름이_X인_브랜드_추가() throws Exception {
                // given
                final String givenBrandName = "X";
                final BrandAddRequest brandAddRequest = new BrandAddRequest();
                Field brandNameField = brandAddRequest.getClass().getDeclaredField("name");
                brandNameField.setAccessible(true);
                brandNameField.set(brandAddRequest, givenBrandName);
                BDDMockito.given(brandService.add(any(BrandAddRequest.class))).willReturn(BrandDTO.create(1L, givenBrandName));
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.data.[0].name").value(givenBrandName))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("이름을 지정하지 않은 채 브랜드 추가")
            void 이름을_지정하지_않음() throws Exception {
                // given
                final BrandAddRequest brandAddRequest = new BrandAddRequest();
                BDDMockito.given(brandService.add(any(BrandAddRequest.class))).willReturn(BrandDTO.empty());
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.REQUEST_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value("brand 명 값이 비어있습니다."))
                        .andDo(print());
            }

            @Test
            @DisplayName("이미 등록된 브랜드명 추가")
            void 이미_등록된_브랜드명() throws Exception {
                // given
                final BrandAddRequest brandAddRequest = new BrandAddRequest();
                Field brandNameField = brandAddRequest.getClass().getDeclaredField("name");
                brandNameField.setAccessible(true);
                brandNameField.set(brandAddRequest, "이미 존재하는 브랜드명");
                BDDMockito.given(brandService.add(any(BrandAddRequest.class))).willThrow(new BrandException(ErrorCode.BRAND_DUPLICATE_ERROR));
                // when & then
                mockMvc.perform(put("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandAddRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.BRAND_DUPLICATE_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_DUPLICATE_ERROR.getMessage()))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("브랜드 수정")
    class 브랜드_수정 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("id가 1인 브랜드 이름을 Y로 변경")
            void id가_1인_브랜드_이름을_Y로_변경() throws Exception {
                // given
                final String givenBrandName = "Y";
                final BrandUpdateRequest brandUpdateRequest = new BrandUpdateRequest();
                Field brandIdField = brandUpdateRequest.getClass().getDeclaredField("id");
                Field brandNameField = brandUpdateRequest.getClass().getDeclaredField("name");
                brandIdField.setAccessible(true);
                brandIdField.set(brandUpdateRequest, 1L);
                brandNameField.setAccessible(true);
                brandNameField.set(brandUpdateRequest, givenBrandName);
                BDDMockito.given(brandService.update(any(BrandUpdateRequest.class))).willReturn(BrandDTO.create(1L, givenBrandName));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.data.[0].name").value(givenBrandName))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("유효하지 않은 브랜드 id 입력")
            void 유효하지_않은_브랜드_id_입력() throws Exception {
                // given
                final BrandUpdateRequest brandUpdateRequest = new BrandUpdateRequest();
                Field brandIdField = brandUpdateRequest.getClass().getDeclaredField("id");
                Field brandNameField = brandUpdateRequest.getClass().getDeclaredField("name");
                brandIdField.setAccessible(true);
                brandIdField.set(brandUpdateRequest, -1L);
                brandNameField.setAccessible(true);
                brandNameField.set(brandUpdateRequest, "유효한 브랜드명");
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.REQUEST_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value("잘못된 브랜드 id 입니다."))
                        .andDo(print());
            }

            @Test
            @DisplayName("유효하지 않은 브랜드명 입력")
            void 유효하지_않은_브랜드명_입력() throws Exception {
                // given
                final BrandUpdateRequest brandUpdateRequest = new BrandUpdateRequest();
                Field brandIdField = brandUpdateRequest.getClass().getDeclaredField("id");
                Field brandNameField = brandUpdateRequest.getClass().getDeclaredField("name");
                brandIdField.setAccessible(true);
                brandIdField.set(brandUpdateRequest, 999L);
                brandNameField.setAccessible(true);
                brandNameField.set(brandUpdateRequest, "");
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.REQUEST_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value("brand 명 값이 비어있습니다."))
                        .andDo(print());
            }

            @Test
            @DisplayName("존재하지 않는 브랜드 id의 브랜드명 변경 시도")
            void 존재하지_않는_브랜드_id의_브랜드명_변경_시도() throws Exception {
                // given
                final String givenBrandName = "Y";
                final BrandUpdateRequest brandUpdateRequest = new BrandUpdateRequest();
                Field brandIdField = brandUpdateRequest.getClass().getDeclaredField("id");
                Field brandNameField = brandUpdateRequest.getClass().getDeclaredField("name");
                brandIdField.setAccessible(true);
                brandIdField.set(brandUpdateRequest, 999L);
                brandNameField.setAccessible(true);
                brandNameField.set(brandUpdateRequest, givenBrandName);
                BDDMockito.given(brandService.update(any(BrandUpdateRequest.class))).willThrow(new BrandException(ErrorCode.BRAND_NOT_FOUND_ERROR));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.BRAND_NOT_FOUND_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_NOT_FOUND_ERROR.getMessage()))
                        .andDo(print());
            }

            @Test
            @DisplayName("기존 존재하는 브랜드명으로 변경 시도")
            void 기존_존재하는_브랜드명으로_변경_시도() throws Exception {
                // given
                final String givenBrandName = "존재하는 브랜드명";
                final BrandUpdateRequest brandUpdateRequest = new BrandUpdateRequest();
                Field brandIdField = brandUpdateRequest.getClass().getDeclaredField("id");
                Field brandNameField = brandUpdateRequest.getClass().getDeclaredField("name");
                brandIdField.setAccessible(true);
                brandIdField.set(brandUpdateRequest, 999L);
                brandNameField.setAccessible(true);
                brandNameField.set(brandUpdateRequest, givenBrandName);
                BDDMockito.given(brandService.update(any(BrandUpdateRequest.class))).willThrow(new BrandException(ErrorCode.BRAND_DUPLICATE_ERROR));
                // when & then
                mockMvc.perform(post("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.BRAND_DUPLICATE_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_DUPLICATE_ERROR.getMessage()))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("브랜드 삭제")
    class 브랜드_삭제 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("id가 1인 브랜드 식제")
            void id가_1인_브랜드_삭제() throws Exception {
                // given
                final BrandDeleteRequest brandDeleteRequest = new BrandDeleteRequest();
                final long givenBrandId = 1L;
                final String givenBrandName = "삭제 대상";
                Field brandIdField = brandDeleteRequest.getClass().getDeclaredField("id");
                brandIdField.setAccessible(true);
                brandIdField.set(brandDeleteRequest, givenBrandId);
                BDDMockito.given(brandService.delete(any(BrandDeleteRequest.class))).willReturn(BrandDTO.create(givenBrandId, givenBrandName));
                // when & then
                mockMvc.perform(delete("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandDeleteRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.data.[0].id").value(givenBrandId))
                        .andExpect(jsonPath("$.data.[0].name").value(givenBrandName))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("유효하지 않은 브랜드 id 입력")
            void 유효하지_않은_브랜드_id_입력() throws Exception {
                // given
                final BrandDeleteRequest brandDeleteRequest = new BrandDeleteRequest();
                Field brandIdField = brandDeleteRequest.getClass().getDeclaredField("id");
                brandIdField.setAccessible(true);
                brandIdField.set(brandDeleteRequest, -1L);
                // when & then
                mockMvc.perform(delete("/backoffice/api/v1/brand")
                                .content(objectMapper.writeValueAsString(brandDeleteRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.REQUEST_ERROR.toString()))
                        .andExpect(jsonPath("$.message").value("잘못된 브랜드 id 입니다."))
                        .andDo(print());
            }
        }
    }
}
