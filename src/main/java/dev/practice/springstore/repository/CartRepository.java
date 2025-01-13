package dev.practice.springstore.repository;

import dev.practice.springstore.models.cart.Cart;
import dev.practice.springstore.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

    Long id(Long id);

    Long user(User user);
}
