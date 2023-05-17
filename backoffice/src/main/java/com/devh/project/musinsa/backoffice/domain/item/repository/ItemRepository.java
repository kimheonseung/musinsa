package com.devh.project.musinsa.backoffice.domain.item.repository;

import com.devh.project.musinsa.backoffice.domain.brand.entity.Brand;
import com.devh.project.musinsa.backoffice.domain.category.entity.Category;
import com.devh.project.musinsa.backoffice.domain.item.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    boolean existsByNameAndCategoryAndBrand(String name, Category category, Brand brand);
}
