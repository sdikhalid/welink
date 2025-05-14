package com.alten.challenge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.alten.challenge.entity.EcomUser;

public interface UserRepository extends CrudRepository<EcomUser, String> {

    Optional<EcomUser> findByEmail(String email);
}
