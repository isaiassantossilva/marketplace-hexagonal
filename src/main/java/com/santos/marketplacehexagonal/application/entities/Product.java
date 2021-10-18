package com.santos.marketplacehexagonal.application.entities;

import com.santos.marketplacehexagonal.application.valueobjects.Price;
import com.santos.marketplacehexagonal.application.valueobjects.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Product {
    private String id;

    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private String name;
    private Store store;
    private Category category;
    private Price price;
    private List<Media> medias;

    private ProductStatus status;
    private Boolean highlighted;

    private Long numberOfLikes;

    public boolean isValid() {
        return true;
    }
}
