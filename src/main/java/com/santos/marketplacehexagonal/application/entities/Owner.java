package com.santos.marketplacehexagonal.application.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    private String userId;
    private String accountId;

    public boolean isValid() {

        if(userId == null || userId.isBlank())
            throw new RuntimeException("User id is invalid!");

        if(accountId == null || accountId.isBlank())
            throw new RuntimeException("Account id is Invalid!");

        return true;
    }
}
