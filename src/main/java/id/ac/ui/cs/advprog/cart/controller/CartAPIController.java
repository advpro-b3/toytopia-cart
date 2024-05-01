package id.ac.ui.cs.advprog.cart.controller;

import id.ac.ui.cs.advprog.cart.model.CartItem;
import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.service.ShoppingCartService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartAPIController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping("/all")
    public ResponseEntity<Object> getAllCarts() {
        List<ShoppingCart> allCarts = shoppingCartService.getAllCarts();
        Map<String, Object> response = new HashMap<>();
        String message = "Successfully retrieved all shopping carts";
        response.put("message", message);
        response.put("carts", allCarts);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/data/{userId}")
    public ResponseEntity<Object> getShoppingCartInformation(@PathVariable Long userId) {
        ShoppingCart cart = shoppingCartService.getShoppingCartInformation(userId);
        Map<String, Object> response = new HashMap<>();
        String message = "Successfully retrieved shopping cart data";
        response.put("message", message);
        if (cart != null) {
            Map<String, CartItem> cartItems = cart.getCartItemMap();
            response.put("cartItems", cartItems);
            response.put("totalPrice", cart.calculateTotalPrice());
        } else {
            response.put("cartItems", new HashMap<>());
            response.put("totalPrice", 0.0);
        }
        return ResponseEntity.ok(response);
    }


    // delete

    @DeleteMapping("/delete/{userId}/{productId}")
    public ResponseEntity<Object> deleteCartItem(
            @PathVariable Long userId,
            @PathVariable String productId
    ) {
        shoppingCartService.deleteCartItemFromShoppingCart(userId, productId);
        Map<String, Object> response = new HashMap<>();
        String message = "Item deleted successfully";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteAllCartItem(
            @PathVariable Long userId
    ) {
        ShoppingCart cart = shoppingCartService.getShoppingCartInformation(userId);
        cart.getCartItemMap().clear();
        shoppingCartService.updateShoppingCart(cart);
        Map<String, Object> response = new HashMap<>();
        String message = "All items deleted successfully";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> updateCartItem(
            @PathVariable Long userId,
            @RequestBody JsonNode requestBody
    ) {
        String productId = requestBody.get("productId").asText();
        String name = requestBody.get("name").asText();
        double price = requestBody.get("price").asDouble();
        int quantity = requestBody.get("quantity").asInt();

        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setName(name);
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);
        shoppingCartService.createOrUpdateCartItemToShoppingCart(userId, cartItem);

        Map<String, Object> response = new HashMap<>();
        String message = "Item updated successfully";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
}