package com.devh.project.musinsa.search.domain.price.lowest.repository;

import com.devh.project.musinsa.search.domain.price.lowest.entity.LowestPriceItemByCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LowestPriceItemByCategoryRepository extends CrudRepository<LowestPriceItemByCategory, String> {
}
