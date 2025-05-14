package com.alten.challenge.controler;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alten.challenge.secrity.JwtUtil;
import com.alten.challenge.service.CredentialService;
import com.alten.challenge.service.EcomService;
import com.alten.challenge.service.model.CartVO;
import com.alten.challenge.service.model.LoginRequest;
import com.alten.challenge.service.model.UserVO;

@RestController
public class CheckoutController {

    private final EcomService ecomService;

    @Inject
    public CheckoutController(EcomService ecomService) {
        this.ecomService = ecomService;
    }

    @GetMapping("/cart/me")
    public ResponseEntity<CartVO> findCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.of(Optional.ofNullable(ecomService.retrieveCart(userDetails.getUsername())));
    }

    @PutMapping("/cart/product/{productId}/add/{quantity}")
    public ResponseEntity<CartVO> updateCart(@PathVariable Long productId, @PathVariable int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ecomService.updateCart(userDetails.getUsername(), productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}