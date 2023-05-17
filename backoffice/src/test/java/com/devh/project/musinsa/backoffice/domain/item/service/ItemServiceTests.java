package com.devh.project.musinsa.backoffice.domain.item.service;

import com.devh.project.musinsa.backoffice.domain.brand.entity.Brand;
import com.devh.project.musinsa.backoffice.domain.brand.repository.BrandRepository;
import com.devh.project.musinsa.backoffice.domain.category.entity.Category;
import com.devh.project.musinsa.backoffice.domain.category.repository.CategoryRepository;
import com.devh.project.musinsa.backoffice.domain.common.exception.BrandException;
import com.devh.project.musinsa.backoffice.domain.common.exception.CategoryException;
import com.devh.project.musinsa.backoffice.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.backoffice.domain.common.exception.ItemException;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemAddRequest;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemDTO;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemDeleteRequest;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemUpdateRequest;
import com.devh.project.musinsa.backoffice.domain.item.entity.Item;
import com.devh.project.musinsa.backoffice.domain.item.repository.ItemRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {
    @InjectMocks
    ItemServiceImpl itemService;
    @Mock
    ItemRepository itemRepository;
    @Mock
    BrandRepository brandRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ItemAddRequest itemAddRequest;
    @Mock
    ItemUpdateRequest itemUpdateRequest;
    @Mock
    ItemDeleteRequest itemDeleteRequest;

    @Nested
    @DisplayName("상품 추가 테스트")
    class 상품_추가 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("새로운 상품을 추가")
            void 새로운_상품을_추가() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenItemName = "새로운 상품명";
                final String givenBrandName = "브랜드명";
                final Brand givenBrand = Brand.newBrand(givenBrandName);
                final String givenCategoryName = "카테고리명";
                final Category givenCategory = Category.newCategory(givenCategoryName);
                final long givenPrice = 10_000L;
                final Item givenItem = Item.newItem(givenItemName, givenCategory, givenBrand, givenPrice);
                Field itemIdField = givenItem.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItem, givenItemId);
                given(itemAddRequest.getItemName()).willReturn(givenItemName);
                given(itemAddRequest.getBrandName()).willReturn(givenBrandName);
                given(itemAddRequest.getCategoryName()).willReturn(givenCategoryName);
                given(itemAddRequest.getPrice()).willReturn(givenPrice);
                given(brandRepository.findByName(givenBrandName)).willReturn(Optional.of(givenBrand));
                given(categoryRepository.findByName(givenCategoryName)).willReturn(Optional.of(givenCategory));
                given(itemRepository.existsByNameAndCategoryAndBrand(givenItemName, givenCategory, givenBrand)).willReturn(false);
                given(itemRepository.save(any(Item.class))).willReturn(givenItem);
                // when
                ItemDTO addResult = itemService.add(itemAddRequest);
                // then
                assertAll(
                        () -> assertEquals(givenItemName, addResult.getName()),
                        () -> assertEquals(givenBrandName, addResult.getBrand()),
                        () -> assertEquals(givenCategoryName, addResult.getCategory()),
                        () -> assertEquals(givenPrice, addResult.getPrice())
                );
            }
        }


        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("이미 존재하는 상품 - (상품명 + 카테고리 + 브랜드 조합이 동일)")
            void 이미_존재하는_상품_상품명_카테고리_브랜드_조합이_동일() {
                // given
                final String givenItemName = "상품명";
                final String givenBrandName = "브랜드명";
                final Brand givenBrand = Brand.newBrand(givenBrandName);
                final String givenCategoryName = "카테고리명";
                final Category givenCategory = Category.newCategory(givenCategoryName);
                given(itemAddRequest.getItemName()).willReturn(givenItemName);
                given(itemAddRequest.getBrandName()).willReturn(givenBrandName);
                given(itemAddRequest.getCategoryName()).willReturn(givenCategoryName);
                given(brandRepository.findByName(givenBrandName)).willReturn(Optional.of(givenBrand));
                given(categoryRepository.findByName(givenCategoryName)).willReturn(Optional.of(givenCategory));
                given(itemRepository.existsByNameAndCategoryAndBrand(givenItemName, givenCategory, givenBrand)).willReturn(true);
                // when & then
                assertThrows(ItemException.class, () -> itemService.add(itemAddRequest), ErrorCode.ITEM_DUPLICATE_ERROR.getMessage());
            }

            @Test
            @DisplayName("존재하지 않는 카테고리 입력")
            void 존재하지_않는_카테고리_입력() {
                // given
                final String givenBrandName = "브랜드명";
                final Brand givenBrand = Brand.newBrand(givenBrandName);
                final String givenCategoryName = "존재하지 않는 카테고리명";
                given(itemAddRequest.getBrandName()).willReturn(givenBrandName);
                given(itemAddRequest.getCategoryName()).willReturn(givenCategoryName);
                given(brandRepository.findByName(givenBrandName)).willReturn(Optional.of(givenBrand));
                given(categoryRepository.findByName(givenCategoryName)).willReturn(Optional.empty());
                // when & then
                assertThrows(CategoryException.class, () -> itemService.add(itemAddRequest), ErrorCode.CATEGORY_NOT_FOUND_ERROR.getMessage());
            }

            @Test
            @DisplayName("존재하지 않는 브랜드 입력")
            void 존재하지_않는_브랜드_입력() {
                // given
                final String givenBrandName = "존재하지 않는 브랜드명";
                given(itemAddRequest.getBrandName()).willReturn(givenBrandName);
                given(brandRepository.findByName(givenBrandName)).willReturn(Optional.empty());
                // when & then
                assertThrows(BrandException.class, () -> itemService.add(itemAddRequest), ErrorCode.BRAND_NOT_FOUND_ERROR.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("상품 수정 테스트")
    class 상품_수정 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("기존 상품명 수정")
            void 기존_상품명_수정() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenItemName = "새로운 상품명";
                final Category givenCategory = Category.newCategory("category");
                final Brand givenBrand = Brand.newBrand("brand");
                final Long givenPrice = 10_000L;
                final Item oldItem = Item.newItem("기존 상품명", givenCategory, givenBrand, givenPrice);
                Field itemIdField = oldItem.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(oldItem, givenItemId);
                given(itemUpdateRequest.getId()).willReturn(givenItemId);
                given(itemUpdateRequest.getItemName()).willReturn(givenItemName);
                given(itemUpdateRequest.getBrandName()).willReturn(null);
                given(itemUpdateRequest.getCategoryName()).willReturn(null);
                given(itemUpdateRequest.getPrice()).willReturn(null);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.of(oldItem));
                 // when
                ItemDTO updateResult = itemService.update(itemUpdateRequest);
                // then
                assertAll(
                        () -> assertEquals(givenItemName, updateResult.getName()),
                        () -> assertEquals(givenCategory.getName(), updateResult.getCategory()),
                        () -> assertEquals(givenBrand.getName(), updateResult.getBrand()),
                        () -> assertEquals(givenPrice, updateResult.getPrice())
                );
            }

            @Test
            @DisplayName("기존 카테고리 수정")
            void 기존_카테고리_수정() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenCategoryName = "새로운 카테고리명";
                final String givenItemName = "기존 상품명";
                final Long givenPrice = 10_0000L;
                final Category oldCategory = Category.newCategory("기존 카테고리명");
                final Category newCategory = Category.newCategory(givenCategoryName);
                final Brand givenBrand = Brand.newBrand("brand");
                final Item givenItem = Item.newItem(givenItemName, oldCategory, givenBrand, givenPrice);
                Field itemIdField = givenItem.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItem, givenItemId);
                given(itemUpdateRequest.getId()).willReturn(givenItemId);
                given(itemUpdateRequest.getCategoryName()).willReturn(givenCategoryName);
                given(itemUpdateRequest.getItemName()).willReturn(null);
                given(itemUpdateRequest.getBrandName()).willReturn(null);
                given(itemUpdateRequest.getPrice()).willReturn(null);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.of(givenItem));
                given(categoryRepository.findByName(givenCategoryName)).willReturn(Optional.of(newCategory));
                // when
                ItemDTO updateResult = itemService.update(itemUpdateRequest);
                // then
                assertAll(
                        () -> assertEquals(givenItemName, updateResult.getName()),
                        () -> assertEquals(givenCategoryName, updateResult.getCategory()),
                        () -> assertEquals(givenBrand.getName(), updateResult.getBrand()),
                        () -> assertEquals(givenPrice, updateResult.getPrice())
                );
            }

            @Test
            @DisplayName("기존 브랜드 수정")
            void 기존_브랜드_수정() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenBrandName = "새로운 브랜드명";
                final String givenItemName = "기존 상품명";
                final Long givenPrice = 10_000L;
                final Brand oldBrand = Brand.newBrand("기존 브랜드명");
                final Brand newBrand = Brand.newBrand(givenBrandName);
                final Category givenCategory = Category.newCategory("category");
                final Item givenItem = Item.newItem(givenItemName, givenCategory, oldBrand, givenPrice);
                Field itemIdField = givenItem.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItem, givenItemId);
                given(itemUpdateRequest.getId()).willReturn(givenItemId);
                given(itemUpdateRequest.getBrandName()).willReturn(givenBrandName);
                given(itemUpdateRequest.getCategoryName()).willReturn(null);
                given(itemUpdateRequest.getItemName()).willReturn(null);
                given(itemUpdateRequest.getPrice()).willReturn(null);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.of(givenItem));
                given(brandRepository.findByName(givenBrandName)).willReturn(Optional.of(newBrand));
                // when
                ItemDTO updateResult = itemService.update(itemUpdateRequest);
                // then
                assertAll(
                        () -> assertEquals(givenItemName, updateResult.getName()),
                        () -> assertEquals(givenCategory.getName(), updateResult.getCategory()),
                        () -> assertEquals(newBrand.getName(), updateResult.getBrand()),
                        () -> assertEquals(givenPrice, updateResult.getPrice())
                );
            }

            @Test
            @DisplayName("기존 가격 수정")
            void 기존_가격_수정() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenItemName = "기존 상품명";
                final Long oldPrice = 500L;
                final Long givenPrice = 11_111L;
                final Brand givenBrand = Brand.newBrand("brand");
                final Category givenCategory = Category.newCategory("category");
                final Item givenItem = Item.newItem(givenItemName, givenCategory, givenBrand, oldPrice);
                Field itemIdField = givenItem.getClass().getDeclaredField("id");
                itemIdField.setAccessible(true);
                itemIdField.set(givenItem, givenItemId);
                given(itemUpdateRequest.getId()).willReturn(givenItemId);
                given(itemUpdateRequest.getPrice()).willReturn(givenPrice);
                given(itemUpdateRequest.getBrandName()).willReturn(null);
                given(itemUpdateRequest.getCategoryName()).willReturn(null);
                given(itemUpdateRequest.getItemName()).willReturn(null);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.of(givenItem));
                // when
                ItemDTO updateResult = itemService.update(itemUpdateRequest);
                // then
                assertAll(
                        () -> assertEquals(givenItemName, updateResult.getName()),
                        () -> assertEquals(givenCategory.getName(), updateResult.getCategory()),
                        () -> assertEquals(givenBrand.getName(), updateResult.getBrand()),
                        () -> assertEquals(givenPrice, updateResult.getPrice())
                );
            }

            @Test
            @DisplayName("기존 상품의 모든 정보 수정")
            void 기존_상품의_모든_정보_수정() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenItemName = "새로운 상품명";
                final String givenCategoryName = "새로운 카테고리명";
                final String givenBrandName = "새로운 브랜드명";
                final Category givenCategory = Category.newCategory(givenCategoryName);
                final Brand givenBrand = Brand.newBrand(givenBrandName);
                final long givenPrice = 11_111L;
                final String oldItemName = "oldItemName";
                final Category oldCategory = Category.newCategory("category");
                final Brand oldBrand = Brand.newBrand("brand");
                final long oldPrice = 500L;
                final Item oldItem = Item.newItem(oldItemName, oldCategory, oldBrand, oldPrice);
                Field oldItemIdField = oldItem.getClass().getDeclaredField("id");
                oldItemIdField.setAccessible(true);
                oldItemIdField.set(oldItem, givenItemId);
                given(itemUpdateRequest.getId()).willReturn(givenItemId);
                given(itemUpdateRequest.getItemName()).willReturn(givenItemName);
                given(itemUpdateRequest.getBrandName()).willReturn(givenBrandName);
                given(itemUpdateRequest.getCategoryName()).willReturn(givenCategoryName);
                given(itemUpdateRequest.getPrice()).willReturn(givenPrice);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.of(oldItem));
                given(brandRepository.findByName(givenBrandName)).willReturn(Optional.of(givenBrand));
                given(categoryRepository.findByName(givenCategoryName)).willReturn(Optional.of(givenCategory));
                // when
                ItemDTO updateResult = itemService.update(itemUpdateRequest);
                // then
                assertAll(
                        () -> assertEquals(givenItemName, updateResult.getName()),
                        () -> assertEquals(givenCategory.getName(), updateResult.getCategory()),
                        () -> assertEquals(givenBrand.getName(), updateResult.getBrand()),
                        () -> assertEquals(givenPrice, updateResult.getPrice())
                );
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("대상 상품을 찾을 수 없음")
            void 대상_상품을_찾을_수_없음() {
                // given
                final Long givenItemId = 999L;
                given(itemUpdateRequest.getId()).willReturn(givenItemId);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.empty());
                // when & then
                assertThrows(ItemException.class, () -> itemService.update(itemUpdateRequest), ErrorCode.ITEM_NOT_FOUND_ERROR.getMessage());
            }

            @Test
            @DisplayName("대상 브랜드를 찾을 수 없음")
            void 대상_브랜드를_찾을_수_없음() {
                // given
                final Long givenItemId = 999L;
                final String givenBrandName = "존재하지 않는 브랜드명";
                final Item givenItem = Item.newItem("itemName", Category.newCategory("category"), Brand.newBrand("brand"), 10_000L);
                given(itemUpdateRequest.getId()).willReturn(givenItemId);
                given(itemUpdateRequest.getBrandName()).willReturn(givenBrandName);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.of(givenItem));
                given(brandRepository.findByName(givenBrandName)).willReturn(Optional.empty());
                // when & then
                assertThrows(BrandException.class, () -> itemService.update(itemUpdateRequest), ErrorCode.BRAND_NOT_FOUND_ERROR.getMessage());
            }

            @Test
            @DisplayName("대상 카테고리를 찾을 수 없음")
            void 대상_카테고리를_찾을_수_없음() {
                // given
                final Long givenItemId = 999L;
                final String givenCategoryName = "존재하지 않는 카테고리명";
                final Item givenItem = Item.newItem("itemName", Category.newCategory("category"), Brand.newBrand("brand"), 10_000L);
                given(itemUpdateRequest.getId()).willReturn(givenItemId);
                given(itemUpdateRequest.getCategoryName()).willReturn(givenCategoryName);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.of(givenItem));
                given(categoryRepository.findByName(givenCategoryName)).willReturn(Optional.empty());
                // when & then
                assertThrows(CategoryException.class, () -> itemService.update(itemUpdateRequest), ErrorCode.CATEGORY_NOT_FOUND_ERROR.getMessage());
            }

            @Test
            @DisplayName("변경할 값을 입력하지 않음")
            void 변경할_값을_입력하지_않음() {
                // given
                final Long givenItemId = 999L;
                final Item givenItem = Item.newItem("item", Category.newCategory("category"), Brand.newBrand("brand"), 5_000L);
                given(itemUpdateRequest.getId()).willReturn(givenItemId);
                given(itemUpdateRequest.getCategoryName()).willReturn(null);
                given(itemUpdateRequest.getBrandName()).willReturn(null);
                given(itemUpdateRequest.getPrice()).willReturn(null);
                given(itemUpdateRequest.getItemName()).willReturn(null);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.of(givenItem));
                // when & then
                assertThrows(ItemException.class, () -> itemService.update(itemUpdateRequest), ErrorCode.ITEM_UPDATE_ERROR.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("상품 삭제 테스트")
    class 상품_삭제 {
        @Nested
        @DisplayName("성공")
        class 성공 {
            @Test
            @DisplayName("주어진 id로 삭제")
            void 주어진_id로_삭제() throws Exception {
                // given
                final Long givenItemId = 999L;
                final String givenItemName = "기존 상품명";
                final String givenCategoryName = "기존 카테고리명";
                final String givenBrandName = "기존 브랜드명";
                final Long givenPrice = 10_000L;
                final Item givenItem = Item.newItem(givenItemName, Category.newCategory(givenCategoryName), Brand.newBrand(givenBrandName), givenPrice);
                Field fieldItemId = givenItem.getClass().getDeclaredField("id");
                fieldItemId.setAccessible(true);
                fieldItemId.set(givenItem, givenItemId);
                given(itemDeleteRequest.getId()).willReturn(givenItemId);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.of(givenItem));
                willDoNothing().given(itemRepository).deleteById(givenItemId);
                // when
                ItemDTO deleteResult = itemService.delete(itemDeleteRequest);
                // then
                assertAll(
                        () -> assertEquals(givenItemId, deleteResult.getId()),
                        () -> assertEquals(givenItemName, deleteResult.getName()),
                        () -> assertEquals(givenCategoryName, deleteResult.getCategory()),
                        () -> assertEquals(givenBrandName, deleteResult.getBrand()),
                        () -> assertEquals(givenPrice, deleteResult.getPrice())
                );
            }
        }

        @Nested
        @DisplayName("실패")
        class 실패 {
            @Test
            @DisplayName("대상 상품을 찾을 수 없음")
            void 대상_상품을_찾을_수_없음() {
                // given
                final Long givenItemId = 999L;
                given(itemDeleteRequest.getId()).willReturn(givenItemId);
                given(itemRepository.findById(givenItemId)).willReturn(Optional.empty());
                // when & then
                assertThrows(ItemException.class, () -> itemService.delete(itemDeleteRequest), ErrorCode.ITEM_NOT_FOUND_ERROR.getMessage());
            }
        }
    }

}
