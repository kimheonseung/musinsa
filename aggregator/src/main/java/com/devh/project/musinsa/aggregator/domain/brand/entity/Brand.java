package com.devh.project.musinsa.aggregator.domain.brand.entity;

import com.devh.project.musinsa.aggregator.domain.item.entity.Item;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(indexes = {
        @Index(name = "idx_brand_name", columnList = "name")
})
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Item> items;

    private Brand() {}

    private Brand(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public static Brand newBrand(String name) {
        return new Brand(name);
    }

    public void updateName(String brandName) {
        this.name = brandName;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }
}
