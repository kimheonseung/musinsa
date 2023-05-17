package com.devh.project.musinsa.search.domain.price.lowest.service;

import com.devh.project.musinsa.search.domain.price.lowest.dto.LowestPriceItemByBrandResponse;
import com.devh.project.musinsa.search.domain.price.lowest.entity.LowestPriceItemByBrand;
import com.devh.project.musinsa.search.domain.price.lowest.repository.LowestPriceItemByBrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LowestPriceItemByBrandServiceTests {
    @InjectMocks
    LowestPriceItemByBrandServiceImpl lowestPriceItemByBrandService;
    @Mock
    LowestPriceItemByBrandRepository lowestPriceItemByBrandRepository;
    @Mock
    LowestPriceItemByBrand lowestPriceItemByBrand;

    @Nested
    @DisplayName("단일 브랜드 모든 카테고리 구매 시 최저가 브랜드 및 상품가 조회")
    class 단일_브랜드_모든_카테고리_구매_시_최저가_브랜드_및_상품가_조회 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("내부적으로 고정된 키값을 통한 조회")
            void 내부적으로_고정된_키값을_통한_조회() {
                // given
                final String givenBrandName = "브랜드A";
                given(lowestPriceItemByBrand.getBrandName()).willReturn("브랜드A");
                given(lowestPriceItemByBrandRepository.findById("brand")).willReturn(Optional.of(lowestPriceItemByBrand));
                // when
                LowestPriceItemByBrandResponse lowestPriceItemByBrandResponse = lowestPriceItemByBrandService.get();
                // then
                assertEquals(givenBrandName, lowestPriceItemByBrandResponse.get최저가().get브랜드());
            }
        }
    }
}
