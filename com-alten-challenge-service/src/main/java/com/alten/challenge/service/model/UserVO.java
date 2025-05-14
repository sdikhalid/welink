package com.alten.challenge.service.model;

import com.alten.challenge.entity.Role;
import com.alten.challenge.entity.EcomUser;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserVO {

    private String userName;
    private String firstName;
    private String email;
    private String password;
    private Role role;

    public EcomUser toEntity() {
        return EcomUser.builder()
            .userName(getUserName())
            .firstName(getFirstName())
            .email(getEmail())
            .password(getPassword())
            .role(getRole())
            .build();
    }

    public static UserVO from(EcomUser ecomUser) {
        return UserVO.builder()
            .userName(ecomUser.getUserName())
            .firstName(ecomUser.getFirstName())
            .email(ecomUser.getEmail())
            .password(ecomUser.getPassword())
            .role(ecomUser.getRole())
            .build();
    }
}
