package com.alten.challenge.controler;

import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alten.challenge.service.EcomService;
import com.alten.challenge.service.model.ProductVO;

@RestController
public class ProductController {

    private final EcomService ecomService;

    @Inject
    public ProductController(EcomService ecomService) {
        this.ecomService = ecomService;
    }

    @GetMapping("/products")
    public ResponseEntity<Set<ProductVO>> findAll() {
        return ResponseEntity.of(Optional.ofNullable(ecomService.retrieveProducts()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductVO> findOne(@PathVariable long id) {
        Optional<ProductVO> product = ecomService.findProduct(id);
        if (product.isPresent()) {
            return ResponseEntity.of(ecomService.findProduct(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductVO product) {
        ecomService.updateProduct(id, product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        ecomService.removeProduct(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductVO product) {
        ecomService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
