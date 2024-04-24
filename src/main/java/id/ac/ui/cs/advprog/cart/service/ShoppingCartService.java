package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart create(ShoppingCart shoppingCart);
    ShoppingCart edit(ShoppingCart shoppingCart);
    void delete(ShoppingCart shoppingCart);
    ShoppingCart findById(Long userId);
    List<ShoppingCart> findAll();
}