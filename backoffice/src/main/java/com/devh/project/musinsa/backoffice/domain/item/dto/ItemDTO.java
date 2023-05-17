package com.devh.project.musinsa.backoffice.domain.item.dto;

import lombok.Getter;

@Getter
public class ItemDTO {
    private long id;
    private String name;
    private String category;
    private String brand;
    private long price;

    private ItemDTO(long id, String name, String category, String brand, long price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    public static ItemDTO create(long id, String name, String category, String brand, long price) {
        return new ItemDTO(id, name, category, brand, price);
    }
}
