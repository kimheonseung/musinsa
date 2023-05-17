package com.devh.project.musinsa.aggregator.domain.price.comparison.repository;

import com.devh.project.musinsa.aggregator.domain.price.comparison.entity.ComparisonPriceItemByCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonPriceItemByCategoryRepository extends CrudRepository<ComparisonPriceItemByCategory, String> {
}
