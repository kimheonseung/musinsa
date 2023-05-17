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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ItemServiceImpl implements ItemService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    public ItemServiceImpl(BrandRepository brandRepository,
                           CategoryRepository categoryRepository,
                           ItemRepository itemRepository) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemDTO add(ItemAddRequest itemAddRequest) {
        Brand brand = this.brandRepository.findByName(itemAddRequest.getBrandName()).orElseThrow(
                () -> new BrandException(ErrorCode.BRAND_NOT_FOUND_ERROR)
        );
        Category category = this.categoryRepository.findByName(itemAddRequest.getCategoryName()).orElseThrow(
                () -> new CategoryException(ErrorCode.CATEGORY_NOT_FOUND_ERROR)
        );
        if (this.itemRepository.existsByNameAndCategoryAndBrand(itemAddRequest.getItemName(), category, brand)) {
            throw new ItemException(ErrorCode.ITEM_DUPLICATE_ERROR);
        }
        Item newItem = Item.newItem(itemAddRequest.getItemName(), category, brand, itemAddRequest.getPrice());
        Item savedItem = this.itemRepository.save(newItem);
        ItemDTO itemDTO = ItemDTO.create(savedItem.getId(),
                                         savedItem.getName(),
                                         savedItem.getCategory().getName(),
                                         savedItem.getBrand().getName(),
                                         savedItem.getPrice());
        return itemDTO;
    }

    @Override
    @Transactional
    public ItemDTO update(ItemUpdateRequest itemUpdateRequest) {
        Item item = this.itemRepository.findById(itemUpdateRequest.getId()).orElseThrow(
                () -> new ItemException(ErrorCode.ITEM_NOT_FOUND_ERROR)
        );
        final String itemName = itemUpdateRequest.getItemName();
        final String brandName = itemUpdateRequest.getBrandName();
        final String categoryName = itemUpdateRequest.getCategoryName();
        final Long price = itemUpdateRequest.getPrice();
        if (Objects.isNull(itemName) && Objects.isNull(brandName) && Objects.isNull(categoryName) && Objects.isNull(price)) {
            throw new ItemException(ErrorCode.ITEM_UPDATE_ERROR);
        }
        if (!StringUtils.isEmpty(itemName)) {
            item.changeName(itemName);
        }
        if (!StringUtils.isEmpty(brandName)) {
            item.changeBrand(
                    this.brandRepository.findByName(brandName).orElseThrow(
                            () -> new BrandException(ErrorCode.BRAND_NOT_FOUND_ERROR)
                    )
            );
        }
        if (!StringUtils.isEmpty(categoryName)) {
            item.changeCategory(
                    this.categoryRepository.findByName(categoryName).orElseThrow(
                            () -> new CategoryException(ErrorCode.CATEGORY_NOT_FOUND_ERROR)
                    )
            );
        }
        if (Objects.nonNull(price)) {
            item.changePrice(price);
        }

        ItemDTO itemDTO = ItemDTO.create(item.getId(),
                                         item.getName(),
                                         item.getCategory().getName(),
                                         item.getBrand().getName(),
                                         item.getPrice());

        return itemDTO;
    }

    @Override
    public ItemDTO delete(ItemDeleteRequest itemDeleteRequest) {
        Item item = this.itemRepository.findById(itemDeleteRequest.getId()).orElseThrow(
                () -> new ItemException(ErrorCode.ITEM_NOT_FOUND_ERROR)
        );
        ItemDTO itemDTO = ItemDTO.create(item.getId(),
                                         item.getName(),
                                         item.getCategory().getName(),
                                         item.getBrand().getName(),
                                         item.getPrice());
        this.itemRepository.deleteById(itemDeleteRequest.getId());
        return itemDTO;
    }
}
