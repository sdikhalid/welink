package com.alten.challenge.loader;

import static com.alten.challenge.util.PasswordEncoder.encodePassword;

import java.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alten.challenge.entity.EcomUser;
import com.alten.challenge.entity.Product;
import com.alten.challenge.entity.Role;
import com.alten.challenge.repository.ProductRepository;
import com.alten.challenge.repository.UserRepository;

@Named
class DatabaseLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Inject
    public DatabaseLoader(ProductRepository employeeRepository,
                          PasswordEncoder passwordEncoder,
                          UserRepository userRepository) {
        this.productRepository = employeeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        userRepository.save(EcomUser.builder()
            .userName("chuck")
            .firstName("hero")
            .email("admin@admin.com")
            .role(Role.ADMIN)
            .password(passwordEncoder.encode("tst"))
            .build());

        userRepository.save(EcomUser.builder()
            .userName("spider man")
            .firstName("super hero")
            .email("chuck@fake.io")
            .role(Role.CUSTOMER)
            .password(passwordEncoder.encode("tst"))
            .build());

        Product product1 = productRepository.save(Product.builder()
            .name("spider mane")
            .code("ref_1")
            .createdAt(LocalDate.now())
            .updatedAt(LocalDate.now().plusDays(1))
            .build());

        Product product2 = productRepository.save(Product.builder()
            .name("spider mane")
            .code("ref_2")
            .createdAt(LocalDate.now())
            .updatedAt(LocalDate.now().plusDays(1))
            .build());
    }
}
