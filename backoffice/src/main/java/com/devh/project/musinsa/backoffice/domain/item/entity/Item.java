package com.devh.project.musinsa.backoffice.domain.item.entity;

import com.devh.project.musinsa.backoffice.domain.brand.entity.Brand;
import com.devh.project.musinsa.backoffice.domain.category.entity.Category;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Table(
        indexes = {
                @Index(name = "idx_brand", columnList = "brand_id"),
                @Index(name = "idx_category", columnList = "category_id"),
                @Index(name = "idx_price", columnList = "price"),
                @Index(name = "idx_brand_category", columnList = "brand_id, category_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_item",
                        columnNames = {"name", "category_id", "brand_id"}
                )
        })
public class Item { // TODO: category, brand, price 인덱스 처리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Brand brand;
    @Column(nullable = false)
    private Long price;

    private Item() {}

    private Item(String name, Category category, Brand brand, long price) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;

        this.brand.addItem(this);
        this.category.addItem(this);
    }

    public static Item newItem(String name, Category category, Brand brand, long price) {
        return new Item(name, category, brand, price);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeBrand(Brand brand) {
        this.brand = brand;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changePrice(long price) {
        this.price = price;
    }

    public void removeCategory() {
        this.category.removeItem(this);
    }

    public void removeBrand() {
        this.brand.removeItem(this);
    }
}
