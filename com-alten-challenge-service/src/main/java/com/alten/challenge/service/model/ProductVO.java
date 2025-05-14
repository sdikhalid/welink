package com.alten.challenge.service.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import com.alten.challenge.entity.InventoryStatus;

@Getter
@Builder
public class ProductVO {

    private final Long id;
    private final String code;
    private final String name;
    private final String description;
    private final String image;
    private final String category;
    private final Double price;
    private final int number;
    private final String internalReference;
    private final Long shellId;
    private final InventoryStatus status;
    private final int rating;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public static ProductVO from(com.alten.challenge.entity.Product product) {
        return ProductVO.builder()
            .id(product.getId())
            .code(product.getCode())
            .name(product.getName())
            .description(product.getDescription())
            .image(product.getImage())
            .category(product.getCategory())
            .price(product.getPrice())
            .number(product.getNumber())
            .internalReference(product.getInternalReference())
            .shellId(product.getShellId())
            .status(product.getStatus())
            .rating(product.getRating())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getCreatedAt())
            .build();
    }

    public com.alten.challenge.entity.Product toEntity() {

        return new com.alten.challenge.entity.Product().builder()
            .id(getId())
            .code(getCode())
            .name(getName())
            .description(getDescription())
            .image(getImage())
            .category(getCategory())
            .price(getPrice())
            .number(getNumber())
            .internalReference(getInternalReference())
            .shellId(getShellId())
            .status(getStatus())
            .rating(getRating())
            .createdAt(getCreatedAt())
            .updatedAt(getCreatedAt())
            .build();
    }
}
