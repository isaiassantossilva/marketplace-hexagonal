package com.santos.marketplacehexagonal.application.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Category {
    private String id;
    private String description;

    public boolean isValid() {

        if(id == null || description.isBlank())
            throw new RuntimeException("Description is invalid!");

        return true;
    }
}
