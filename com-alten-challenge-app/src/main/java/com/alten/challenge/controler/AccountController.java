package com.alten.challenge.controler;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alten.challenge.entity.EcomUser;
import com.alten.challenge.secrity.JwtUtil;
import com.alten.challenge.service.CredentialService;
import com.alten.challenge.service.model.LoginRequest;
import com.alten.challenge.service.model.UserVO;

@RestController
public class AccountController {

    private final CredentialService credentialService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Inject
    public AccountController(CredentialService credentialService,
                             AuthenticationManager authenticationManager,
                             PasswordEncoder passwordEncoder,
                             JwtUtil jwtUtil) {
        this.credentialService = credentialService;
        this.authenticationManager=authenticationManager;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
    }

    @GetMapping("/account")
    public ResponseEntity<Set<UserVO>> findAll() {

        return ResponseEntity.of(Optional.ofNullable(credentialService.retrieveUsers()));
    }

    @PostMapping("/account")
    public ResponseEntity<?> createNewUser(@RequestBody UserVO user) {
        try {
            credentialService.createUser(user);
        } catch (RuntimeException ec) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.getPassword()
                    )
                );
            String token= jwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    token
                )
                .body(token);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
