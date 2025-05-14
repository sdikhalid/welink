package com.alten.challenge.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.alten.challenge.entity.Cart;
import com.alten.challenge.entity.CartLine;
import com.alten.challenge.entity.EcomUser;
import com.alten.challenge.entity.Product;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepositoriesTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartLineRepository cartLineRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void should_test_product() {
        // given
        Product p1 = Product.builder().name("test").build();
        entityManager.persist(p1);
        entityManager.flush();

        // when
        Optional<Product> found = productRepository.findById(1L);

        // then
        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), p1.getName());
    }

    @Test
    public void should_test_user() {
        // given
        EcomUser u1 = EcomUser.builder().userName("test").email("test@test.com").build();
        entityManager.persist(u1);
        entityManager.flush();

        // when
        Optional<EcomUser> found = userRepository.findByEmail(u1.getEmail());

        // then
        assertTrue(found.isPresent());
        assertEquals(found.get().getEmail(), u1.getEmail());
    }

    @Test
    public void should_test_checkout() {
        // given
        Product p1 = Product.builder().name("test").build();
        entityManager.persist(p1);
        entityManager.flush();
        EcomUser u1 = EcomUser.builder().userName("test").email("test@test.com").build();
        entityManager.persist(u1);
        entityManager.flush();

        Cart cart = Cart.builder()
            .user(u1)
            .build();
        entityManager.persist(cart);
        entityManager.flush();

        CartLine cartLine = CartLine.builder()
            .quantity(5)
            .cart(cart)
            .product(p1).build();
        entityManager.persist(cartLine);
        entityManager.flush();

        cart.setLines(Stream.of(cartLine).collect(Collectors.toSet()));
        entityManager.persist(cart);
        entityManager.flush();
        // when
        Optional<Cart> found = cartRepository.findById(cart.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals(found.get().getLines().size(), 1);
    }
}
