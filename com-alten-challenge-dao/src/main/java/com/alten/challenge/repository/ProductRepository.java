package com.alten.challenge.repository;

import org.springframework.data.repository.CrudRepository;

import com.alten.challenge.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {


}
