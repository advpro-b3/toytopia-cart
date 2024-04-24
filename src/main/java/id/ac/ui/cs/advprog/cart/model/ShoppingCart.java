package id.ac.ui.cs.advprog.cart.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ShoppingCart {
    private Long userId;
    private Map<String, CartItem> cartItemMap;

    public ShoppingCart() {
        this.cartItemMap = new HashMap<>();
    }

    public CartItem addItem(CartItem item) {
        cartItemMap.put(item.getProductId(), item);
        return item;
    }

    public void deleteItem(CartItem item) {
        cartItemMap.remove(item.getProductId());
    }

    public void editItem(CartItem item) {
        String productId = item.getProductId();
        if (cartItemMap.containsKey(productId)) {
            cartItemMap.put(productId, item);
        } else {
            throw new IllegalArgumentException("Item with productId " + productId + " does not exist in the shopping cart.");
        }
    }
}
