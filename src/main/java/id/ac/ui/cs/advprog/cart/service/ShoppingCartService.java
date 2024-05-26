package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.model.CartItem;
import id.ac.ui.cs.advprog.cart.model.ShoppingCart;


import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCart> getAllCarts();
    CartItem createOrUpdateCartItemToShoppingCart(Long userid, CartItem cartItem);

    void deleteCartItemFromShoppingCart(Long userId, String productId);
    List <CartItem> getCartItemsFromShoppingCart(Long userId);
    ShoppingCart createShoppingCart(Long userId);

    ShoppingCart getShoppingCartInformation(Long userId);
    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

}