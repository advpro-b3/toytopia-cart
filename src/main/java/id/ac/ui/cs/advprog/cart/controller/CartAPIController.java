package id.ac.ui.cs.advprog.cart.controller;

import id.ac.ui.cs.advprog.cart.model.CartItem;
import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.service.ShoppingCartService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<Object> getShoppingCartInformation(@PathVariable Long userId,
                                                             @RequestParam(required = false) String voucherCode) {
        ShoppingCart cart = shoppingCartService.getShoppingCartInformation(userId);
        Map<String, Object> response = new HashMap<>();
        String message = "Successfully retrieved shopping cart data";

        response.put("message", message);
        response.put("userId", userId);
        response.put("voucherCode", voucherCode);

        if (cart != null) {
            Map<String, CartItem> cartItems = cart.getCartItemMap();
            response.put("cartItems", cartItems);
            response.put("totalPrice", cart.calculateTotalPrice(voucherCode));
        } else {
            response.put("cartItems", new HashMap<>());
            response.put("totalPrice", 0.0);
        }

        return ResponseEntity.ok(response);
    }



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
        int quantity = requestBody.get("quantity").asInt();

        ShoppingCart cart = shoppingCartService.getShoppingCartInformation(userId);
        Map<String, Object> response = new HashMap<>();

        if (cart != null) {
            CartItem item = cart.getCartItemMap().get(productId);
            if (item != null) {
                item.setQuantity(quantity);
                shoppingCartService.updateShoppingCart(cart);
                response.put("message", "Item updated successfully");
                response.put("userId", userId);
                response.put("productId", productId);
                response.put("quantity", quantity);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Item not found in cart");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } else {
            response.put("message", "Shopping cart not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/updateAll/{userId}")
    public ResponseEntity<Object> updateAllCartItems(
            @PathVariable Long userId,
            @RequestBody JsonNode requestBody
    ) {
        ShoppingCart cart = shoppingCartService.getShoppingCartInformation(userId);
        Map<String, Object> response = new HashMap<>();

        if (cart != null) {
            JsonNode cartItemMap = requestBody.get("cartItemMap");
            if (cartItemMap != null && cartItemMap.isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fields = cartItemMap.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    JsonNode cartItemNode = field.getValue();
                    String productId = cartItemNode.get("productId").asText();
                    int quantity = cartItemNode.get("quantity").asInt();

                    CartItem item = cart.getCartItemMap().get(productId);
                    if (item != null) {
                        item.setQuantity(quantity);
                    } else {
                        response.put("message", "Item with productId " + productId + " not found in cart");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                }
                shoppingCartService.updateShoppingCart(cart);
                response.put("message", "All items updated successfully");
                response.put("userId", userId);
                response.put("updatedItems", cartItemMap);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid request body format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } else {
            response.put("message", "Shopping cart not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/addItem/{userId}")
    public ResponseEntity<Object> addItemToCart(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> requestBody
    ) {
        String productId = (String) requestBody.get("productId");
        String productName = (String) requestBody.get("name");
        int quantity = (int) requestBody.get("quantity");
        double price = (double) requestBody.get("price");

        ShoppingCart cart = shoppingCartService.getShoppingCartInformation(userId);
        Map<String, Object> response = new HashMap<>();

        if (cart != null) {
            CartItem item = cart.getCartItemMap().get(productId);
            if (item != null) {

                item.setQuantity(item.getQuantity() + quantity);
            } else {

                Long id = (long) cart.getCartItemMap().size();


                item = new CartItem(id++, productId, productName, quantity, price);
                cart.getCartItemMap().put(productId, item);
            }
            shoppingCartService.updateShoppingCart(cart);
            response.put("message", "Item added to cart successfully");
            response.put("userId", userId);
            response.put("productId", productId);
            response.put("name", productName); 
            response.put("quantity", quantity);
            response.put("price", price);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Shopping cart not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


}

