package com.santos.marketplacehexagonal.application.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
public class Like {
    private String id;
    private Object entity;
    private User user;
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(entity, like.entity) && Objects.equals(user, like.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, user);
    }
}
