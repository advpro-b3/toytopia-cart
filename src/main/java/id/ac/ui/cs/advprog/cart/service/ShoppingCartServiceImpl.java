package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.model.CartItem;
import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.model.ShoppingCartBuilder;
import id.ac.ui.cs.advprog.cart.repository.CartItemRepository;
import id.ac.ui.cs.advprog.cart.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
    }



    public List<ShoppingCart> getAllCarts() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }



    @Override
    public CartItem createOrUpdateCartItemToShoppingCart(Long userId, CartItem cartItem) {
//        ShoppingCart cart = findShoppingCartByUserId(Long userId);
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByUserId(userId);
        CartItem existingItem = cart.getCartItemMap().get(cartItem.getProductId());

        if (existingItem == null) {
            cart.getCartItemMap().put(cartItem.getProductId(), cartItem);
        } else {
            existingItem.updateValues(cartItem.getName(), cartItem.getPrice(), cartItem.getQuantity());
            if (existingItem.getQuantity() == 0) {
                deleteCartItemFromShoppingCart(userId, cartItem.getProductId());
                return null;
            }
        }

        shoppingCartRepository.save(cart);
        return existingItem;
    }

    @Override
    public void deleteCartItemFromShoppingCart(Long userId, String productId) {
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByUserId(userId);
        cart.getCartItemMap().remove(productId);
        shoppingCartRepository.save(cart);
    }

    @Override
    public List<CartItem> getCartItemsFromShoppingCart(Long userId) {
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("No shopping cart found for user with ID " + userId);
        }
        return new ArrayList<>(cart.getCartItemMap().values());
    }

    @Override
    public ShoppingCart getShoppingCartInformation(Long userId) {
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("No shopping cart found for user with ID " + userId);
        }
        return cart;
    }

    @Override
    public void createShoppingCart(Long userId) {
        ShoppingCart cart = new ShoppingCartBuilder()
                .withCartItem(new HashMap<>())
                .withUserId(userId)
                .build();
        shoppingCartRepository.save(cart);
    }

}


