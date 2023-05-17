package com.devh.project.musinsa.backoffice.domain.category.repository;

import com.devh.project.musinsa.backoffice.domain.category.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName(String categoryName);
}
