package com.devh.project.musinsa.search.domain.price.comparison.repository;

import com.devh.project.musinsa.search.domain.price.comparison.entity.ComparisonPriceItemByCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonPriceItemByCategoryRepository extends CrudRepository<ComparisonPriceItemByCategory, String> {
}
