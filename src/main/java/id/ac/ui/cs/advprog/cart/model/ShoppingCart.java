package id.ac.ui.cs.advprog.cart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Setter
@Getter
@Entity
public class ShoppingCart {
    @Id
    private Long userId;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "productId")
    private Map<String, CartItem> cartItemMap;

    public ShoppingCart() {
        this.cartItemMap = new HashMap<>();
    }

    public void addItem(CartItem item) {
        cartItemMap.put(item.getProductId(), item);
    }

    public void removeItem(String productId) {
        cartItemMap.remove(productId);
    }

    public double calculateTotalPrice() {
        return cartItemMap.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public Map<String, CartItem> getAllCartItem() {
        return cartItemMap;
    }

    public CartItem addItemToCart(CartItem item) {
        return cartItemMap.put(item.getProductId(), item);
    }


}
