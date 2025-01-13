package dev.practice.springstore.services.cart;

import dev.practice.springstore.exceptions.ResourceNotFoundException;
import dev.practice.springstore.models.cart.Cart;
import dev.practice.springstore.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServices implements iCartServices{
    private final CartRepository repository;

    @Override
    public Cart getCart(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart of id %s not found".formatted(id)));
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        repository.delete(getCart(id));
    }

    @Transactional
    @Override
    public BigDecimal getTotalPrice(Long id) {
        return getCart(id).getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        return repository.save(newCart).getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
