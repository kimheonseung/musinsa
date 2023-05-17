package com.devh.project.musinsa.aggregator.domain.price.lowest.repository;

import com.devh.project.musinsa.aggregator.domain.price.lowest.entity.LowestPriceItemByBrand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LowestPriceItemByBrandRepository extends CrudRepository<LowestPriceItemByBrand, String> {
}
