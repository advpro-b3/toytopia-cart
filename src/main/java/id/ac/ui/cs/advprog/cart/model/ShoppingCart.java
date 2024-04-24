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

}
