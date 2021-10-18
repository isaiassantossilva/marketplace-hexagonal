package com.santos.marketplacehexagonal.application.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class User {
    private String userId;
    private String accountId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(accountId, user.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, accountId);
    }
}
