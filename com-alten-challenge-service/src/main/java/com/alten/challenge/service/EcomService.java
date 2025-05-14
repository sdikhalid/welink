package com.alten.challenge.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import com.alten.challenge.entity.Cart;
import com.alten.challenge.entity.CartLine;
import com.alten.challenge.entity.EcomUser;
import com.alten.challenge.entity.Product;
import com.alten.challenge.repository.CartLineRepository;
import com.alten.challenge.repository.CartRepository;
import com.alten.challenge.repository.ProductRepository;
import com.alten.challenge.repository.UserRepository;
import com.alten.challenge.service.model.CartVO;
import com.alten.challenge.service.model.ProductVO;

@Named
public class EcomService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final CartLineRepository cartLineRepository;

    @Inject
    public EcomService(ProductRepository productRepository,
                       CartRepository cartRepository,
                       CartLineRepository cartLineRepository,
                       UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartLineRepository = cartLineRepository;
    }

    public Set<ProductVO> retrieveProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
            .map(ProductVO::from)
            .collect(Collectors.toSet());
    }

    public Optional<ProductVO> findProduct(long id) {
        return productRepository.findById(id).map(p -> ProductVO.from(p));
    }

    public void updateProduct(long id, ProductVO product) {
        Product entity = product.toEntity();
        entity.setId(id);
        productRepository.save(entity);
    }

    public void removeProduct(long id) {
        productRepository.deleteById(id);
    }

    public void createProduct(ProductVO product) {
        Product entity = product.toEntity();
        entity.setId(null);
        productRepository.save(entity);
    }

    public CartVO retrieveCart(String username) {
        Optional<EcomUser> user = userRepository.findByEmail(username);
        return CartVO.from(getCart(user.get()));
    }

    public void updateCart(String username, Long productId, int quantity) {
        Optional<EcomUser> user = userRepository.findByEmail(username);
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new IllegalArgumentException("product does not exists");
        }
        Cart cart = getCart(user.get());
        Optional<CartLine> cartLine = cart.getLines().stream()
            .filter(line -> line.getProduct().getId().equals(productId))
            .findFirst();
        if (cartLine.isPresent()) {
            int diff = cartLine.get().getQuantity() + quantity;
            if (diff <= 0) {
                cart.getLines().stream().filter(line -> line.getProduct().getId().equals(productId))
                    .forEach(line -> cartLineRepository.deleteById(line.getId()));
                cart.getLines().removeIf(line -> line.getProduct().getId().equals(productId));
            } else {
                cartLine.get().setQuantity(diff);
                cartLineRepository.save(cartLine.get());
            }
        } else if (quantity > 0) {
            cartLineRepository.save(CartLine.builder()
                .cart(cart)
                .product(product.get())
                .quantity(quantity)
                .build());
        }
        cartRepository.save(cart);
    }

    private Cart getCart(EcomUser user) {
        Cart result;
        Optional<Cart> cart = cartRepository.findByUser(user);
        if (cart.isPresent()) {
            result = cart.get();
        } else {
            result = cartRepository.save(Cart.builder()
                .user(user)
                .lines(new HashSet<>())
                .build());
        }
        return result;
    }
}
