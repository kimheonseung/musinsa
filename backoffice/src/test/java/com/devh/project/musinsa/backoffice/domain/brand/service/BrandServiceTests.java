package com.devh.project.musinsa.backoffice.domain.brand.service;

import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandAddRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDTO;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandDeleteRequest;
import com.devh.project.musinsa.backoffice.domain.brand.dto.BrandUpdateRequest;
import com.devh.project.musinsa.backoffice.domain.brand.entity.Brand;
import com.devh.project.musinsa.backoffice.domain.brand.repository.BrandRepository;
import com.devh.project.musinsa.backoffice.domain.common.exception.BrandException;
import com.devh.project.musinsa.backoffice.domain.common.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTests {
    @InjectMocks
    BrandServiceImpl brandService;
    @Mock
    BrandRepository brandRepository;
    @Mock
    BrandAddRequest brandAddRequest;
    @Mock
    BrandUpdateRequest brandUpdateRequest;
    @Mock
    BrandDeleteRequest brandDeleteRequest;

    @Nested
    @DisplayName("브랜드 추가 테스트")
    class 브랜드_추가 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("새로운 브랜드명을 가진 브랜드 생성")
            void 새로운_브랜드명을_가진_브랜드_생성() throws Exception {
                // given
                final long givenBrandId = 999L;
                final String givenBrandName = "newBrandName";
                final Brand givenBrand = Brand.newBrand(givenBrandName);
                Field brandIdField = givenBrand.getClass().getDeclaredField("id");
                brandIdField.setAccessible(true);
                brandIdField.set(givenBrand, givenBrandId);
                given(brandAddRequest.getName()).willReturn(givenBrandName);
                given(brandRepository.save(any(Brand.class))).willReturn(givenBrand);
                // when
                BrandDTO addResult = brandService.add(brandAddRequest);
                // then
                assertEquals(givenBrandName, addResult.getName());
            }
        }


        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("이미 존재하는 브랜드")
            void 이미_존재하는_브랜드_중복_생성() {
                // given
                final String givenBrandName = "newBrandName";
                given(brandAddRequest.getName()).willReturn(givenBrandName);
                given(brandRepository.existsByName(givenBrandName)).willReturn(true);
                // when & then
                assertThrows(BrandException.class, () -> brandService.add(brandAddRequest), ErrorCode.BRAND_DUPLICATE_ERROR.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("브랜드 수정 테스트")
    class 브랜드_수정 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("기존 브랜드명 수정")
            void 기존_브랜드명_수정() throws Exception {
                // given
                final Long givenBrandId = 999L;
                final String givenBrandName = "새로운 브랜드명";
                final Brand givenBrand = Brand.newBrand("기존 브랜드명");
                Field brandIdField = givenBrand.getClass().getDeclaredField("id");
                brandIdField.setAccessible(true);
                brandIdField.set(givenBrand, givenBrandId);
                given(brandUpdateRequest.getId()).willReturn(givenBrandId);
                given(brandUpdateRequest.getName()).willReturn(givenBrandName);
                given(brandRepository.findById(givenBrandId)).willReturn(Optional.of(givenBrand));
                 // when
                BrandDTO updateResult = brandService.update(brandUpdateRequest);
                // then
                assertAll(
                        () -> assertEquals(givenBrandName, updateResult.getName())
                );
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("대상 브랜드를 찾을 수 없음")
            void 대상_브랜드를_찾을_수_없음() {
                // given
                final Long givenBrandId = -999L;
                given(brandUpdateRequest.getId()).willReturn(givenBrandId);
                given(brandRepository.findById(givenBrandId)).willReturn(Optional.empty());
                // when & then
                assertThrows(BrandException.class, () -> brandService.update(brandUpdateRequest), ErrorCode.BRAND_NOT_FOUND_ERROR.getMessage());
            }

            @Test
            @DisplayName("변경하려는 브랜드명이 이미 존재")
            void 변경하려는_브랜드명이_이미_존재() {
                // given
                final Long givenBrandId = 999L;
                final String oldBrandName = "기존 브랜드명";
                final String givenBrandName = "이미 존재하는 브랜드명";
                final Brand oldBrand = Brand.newBrand(oldBrandName);
                given(brandUpdateRequest.getId()).willReturn(givenBrandId);
                given(brandUpdateRequest.getName()).willReturn(givenBrandName);
                given(brandRepository.findById(givenBrandId)).willReturn(Optional.of(oldBrand));
                given(brandRepository.existsByName(givenBrandName)).willReturn(true);
                // when & then
                assertAll(
                        () -> assertThrows(BrandException.class, () -> brandService.update(brandUpdateRequest), ErrorCode.BRAND_DUPLICATE_ERROR.getMessage()),
                        () -> assertNotEquals(givenBrandName, oldBrand.getName())
                );
                ;
            }
        }
    }

    @Nested
    @DisplayName("브랜드 삭제 테스트")
    class 브랜드_삭제 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("주어진 id로 삭제")
            void 주어진_id로_삭제() throws Exception {
                // given
                final Long givenBrandId = 999L;
                final Brand givenBrand = Brand.newBrand("brand");
                Field brandNameField = givenBrand.getClass().getDeclaredField("id");
                brandNameField.setAccessible(true);
                brandNameField.set(givenBrand, givenBrandId);
                given(brandDeleteRequest.getId()).willReturn(givenBrandId);
                given(brandRepository.findById(givenBrandId)).willReturn(Optional.of(givenBrand));
                willDoNothing().given(brandRepository).deleteById(givenBrandId);
                // when
                BrandDTO deleteResult = brandService.delete(brandDeleteRequest);
                // then
                assertEquals(givenBrandId, deleteResult.getId());
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("대상 브랜드를 찾을 수 없음")
            void 대상_브랜드를_찾을_수_없음() {
                // given
                final Long givenBrandId = -999L;
                given(brandDeleteRequest.getId()).willReturn(givenBrandId);
                given(brandRepository.findById(givenBrandId)).willReturn(Optional.empty());
                // when & then
                assertThrows(BrandException.class, () -> brandService.delete(brandDeleteRequest), ErrorCode.BRAND_NOT_FOUND_ERROR.getMessage());
            }
        }
    }

}
