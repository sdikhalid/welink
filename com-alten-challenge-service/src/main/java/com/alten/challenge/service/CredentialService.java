package com.alten.challenge.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.alten.challenge.entity.EcomUser;
import com.alten.challenge.repository.UserRepository;
import com.alten.challenge.service.model.UserVO;

@Named
public class CredentialService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Inject
    public CredentialService(UserRepository userRepository,PasswordEncoder passwordEncoder) {

        this.passwordEncoder=passwordEncoder;
        this.userRepository = userRepository;
    }

    public void createUser(UserVO user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username with same e-mail already exists");
        }
        String encodedhash = passwordEncoder.encode(user.getPassword());
        EcomUser entity = user.toEntity();
        entity.setPassword(encodedhash.toString());
        userRepository.save(entity);
    }

    public Set<UserVO> retrieveUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
            .map(UserVO::from)
            .collect(Collectors.toSet());
    }
}
