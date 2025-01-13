package dev.practice.springstore.repository;

import dev.practice.springstore.models.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
