package com.devh.project.musinsa.backoffice.domain.category.entity;

import com.devh.project.musinsa.backoffice.domain.item.entity.Item;
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
        @Index(name = "idx_category_name", columnList = "name")
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Item> items;

    private Category() {}

    private Category(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public static Category newCategory(String name) {
        return new Category(name);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }
}
