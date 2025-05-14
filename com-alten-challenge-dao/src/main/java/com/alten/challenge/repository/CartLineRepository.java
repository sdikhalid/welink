package com.alten.challenge.repository;

import org.springframework.data.repository.CrudRepository;

import com.alten.challenge.entity.CartLine;

public interface CartLineRepository extends CrudRepository<CartLine, Long> {

}
