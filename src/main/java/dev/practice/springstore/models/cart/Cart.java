package dev.practice.springstore.models.cart;


import dev.practice.springstore.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne
    private User user;

    public void updateTotalAmount() {
        this.totalAmount = BigDecimal.ZERO;
        for (CartItem item: cartItems) {
            BigDecimal currUnitPrice = item.getUnitPrice() != null ? item.getUnitPrice() : BigDecimal.ZERO;
            BigDecimal currTotal = currUnitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
            this.totalAmount = this.totalAmount.add(currTotal);
        }
    }

    public void removeItem (CartItem item) {
        if (item != null){
            this.cartItems.remove(item);
            item.setCart(null);
            updateTotalAmount();
        }
    }

    public void addItem(CartItem item) {
        if (item == null || item.getUnitPrice() == null || item.getQuantity() <= 0) {
            throw  new IllegalArgumentException("Invalid cart item");
        }
        this.cartItems.add(item);
        item.setCart(this);
        updateTotalAmount();
    }
}
