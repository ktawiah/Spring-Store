package dev.practice.springstore.services.cart;

import dev.practice.springstore.models.cart.Cart;

import java.math.BigDecimal;

public interface iCartServices {
    Cart getCart (Long id);

    void clearCart (Long id);

    BigDecimal getTotalPrice (Long id);

    Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}
