package id.ac.ui.cs.advprog.cart.model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartBuilder {
    private Long userId;
    private Map<String, CartItem> cartItemMap;

    public ShoppingCartBuilder() {
        cartItemMap = new HashMap<>();
    }

    public ShoppingCartBuilder withUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public ShoppingCartBuilder withCartItem(String productId, CartItem item) {
        cartItemMap.put(productId, item);
        return this;
    }

    public ShoppingCart build() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        shoppingCart.setCartItemMap(cartItemMap);
        return shoppingCart;
    }
}
