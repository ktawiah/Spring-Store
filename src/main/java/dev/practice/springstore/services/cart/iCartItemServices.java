package dev.practice.springstore.services.cart;

import dev.practice.springstore.models.cart.CartItem;

interface iCartItemServices {
    CartItem addItemToCart (Long cartId, Long productId, int quantity);

    void removeItemFromCart (Long cartId, Long productId);

    void updateItemQuantity (Long cartId, Long productId, int quantity);

    CartItem getCartItem (Long cartId, Long productId);
}
