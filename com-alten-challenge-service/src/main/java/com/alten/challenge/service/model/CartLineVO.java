package com.alten.challenge.service.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.alten.challenge.entity.CartLine;
import com.alten.challenge.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class CartLineVO {
    private Long id;
    private String productCode;
    private String productName;
    private int quantity;

    public static Set<CartLineVO> from(Set<CartLine> lines) {
       return lines.stream().map( line -> CartLineVO.builder()
           .id(line.getId())
           .productCode(line.getProduct().getCode())
           .productName(line.getProduct().getName())
           .quantity(line.getQuantity())
           .build()).collect(Collectors.toSet());

    }
}
