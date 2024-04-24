package id.ac.ui.cs.advprog.cart.repository;

import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShoppingCartRepository {

    private final Map<Long, ShoppingCart> shoppingCartData = new HashMap<>();
    private long nextUserId = 1;

    public ShoppingCart create(ShoppingCart shoppingCart) {
        shoppingCart.setUserId(nextUserId++);
        shoppingCartData.put(shoppingCart.getUserId(), shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart findById(Long userId) {
        return shoppingCartData.get(userId);
    }

    public void deleteById(Long userId) {
        shoppingCartData.remove(userId);
    }

    public List<ShoppingCart> findAll() {
        return new ArrayList<>(shoppingCartData.values());
    }
}

