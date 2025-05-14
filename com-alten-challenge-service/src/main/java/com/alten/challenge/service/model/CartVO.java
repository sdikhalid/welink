package com.alten.challenge.service.model;

import java.util.Optional;
import java.util.Set;

import com.alten.challenge.entity.Cart;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CartVO {

    private final Long id;
    private final String userName;
    private final Set<CartLineVO> lines;

    public static CartVO from(Cart cart) {
        return CartVO.builder()
            .id(cart.getId())
            .userName(cart.getUser().getUserName())
            .lines(CartLineVO.from(cart.getLines()))
            .build();
    }
}
