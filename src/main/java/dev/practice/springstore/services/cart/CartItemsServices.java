package dev.practice.springstore.services.cart;

import dev.practice.springstore.exceptions.ResourceNotFoundException;
import dev.practice.springstore.models.cart.Cart;
import dev.practice.springstore.models.cart.CartItem;
import dev.practice.springstore.models.product.Product;
import dev.practice.springstore.repository.CartItemRepository;
import dev.practice.springstore.repository.CartRepository;
import dev.practice.springstore.services.product.ProductServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemsServices implements iCartItemServices {
    private final CartServices cartServices;
    private final ProductServices productServices;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Transactional
    @Override
    public CartItem addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartServices.getCart(cartId);
        Product product = productServices.getProductById(productId);

        CartItem cartItem = getCartItem(cartId, productId);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cart.addItem(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Transactional
    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartServices.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        if (itemToRemove != null) {
            cart.removeItem(itemToRemove);
            cartRepository.save(cart);
        } else  {
            throw new ResourceNotFoundException("Cart item not found");
        }
    }

    @Transactional
    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartServices.getCart(cartId);
        CartItem itemToUpdate = getCartItem(cartId, productId);

        if (itemToUpdate == null) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        itemToUpdate.setQuantity(quantity);
        itemToUpdate.setUnitPrice(itemToUpdate.getProduct().getPrice());
        itemToUpdate.setTotalPrice();
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartServices.getCart(cartId);
        return cart.getCartItems().stream()
                .filter(cartItem1 -> cartItem1.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}
