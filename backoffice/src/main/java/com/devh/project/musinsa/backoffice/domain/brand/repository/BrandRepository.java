package com.devh.project.musinsa.backoffice.domain.brand.repository;

import com.devh.project.musinsa.backoffice.domain.brand.entity.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Long> {
    boolean existsByName(String brandName);
    Optional<Brand> findByName(String brandName);
}
