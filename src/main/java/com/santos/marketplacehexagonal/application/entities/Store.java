package com.santos.marketplacehexagonal.application.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Store {
    private String id;

    private Owner owner;

    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private String name;
    private String description;
    private String brandIcon;
    private String color;
    private Set<Category> categories;

    private Set<Like> likes = new HashSet<>();

    private Boolean liked;
    private Long numberOfLikes;
    private Long viewsCount;


    public Store(String id, Owner owner, String name, String description, String brandIcon, String color, Set<Category> categories) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.brandIcon = brandIcon;
        this.color = color;
        this.categories = categories;

        this.version = 0;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public boolean isValid() {
        if(this.name == null || name.isBlank())
            throw new RuntimeException("Name is invalid!");

        if(owner == null || !owner.isValid())
            throw new RuntimeException("Owner store is invalid!");

        if(categories == null || categories.stream().anyMatch(c -> !c.isValid()))
            throw new RuntimeException("Category is invalid!");

        return true;
    }

    public Store replace(Store store) {
        if(store.getName() != null)
            name = store.getName();

        if(store.getDescription() != null)
            description = store.getDescription();

        if(store.getBrandIcon() != null)
            brandIcon = store.getBrandIcon();

        if(store.getColor() != null)
            color = store.getColor();

        if(store.getCategories() != null && !store.getCategories().isEmpty())
            categories = store.getCategories();

        modifiedAt = LocalDateTime.now();
        version = this.version + 1;

        return this;
    }
}
