package com.alten.challenge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.alten.challenge.entity.Cart;
import com.alten.challenge.entity.EcomUser;
import com.alten.challenge.entity.Product;

public interface CartRepository extends CrudRepository<Cart, Long> {

    Optional<Cart> findByUser(EcomUser user);
}
