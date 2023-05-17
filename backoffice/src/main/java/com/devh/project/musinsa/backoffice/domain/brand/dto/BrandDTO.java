package com.devh.project.musinsa.backoffice.domain.brand.dto;

import lombok.Getter;

@Getter
public class BrandDTO {
    private long id;
    private String name;
    private BrandDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    private BrandDTO() {}

    public static BrandDTO empty() {
        return new BrandDTO();
    }

    public static BrandDTO create(long id, String name) {
        return new BrandDTO(id, name);
    }
}
