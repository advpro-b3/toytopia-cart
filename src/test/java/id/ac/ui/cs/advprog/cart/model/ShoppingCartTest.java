package id.ac.ui.cs.advprog.cart.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    @Test
    void testCreateShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        assertNotNull(shoppingCart);
        assertTrue(shoppingCart.getCartItemMap().isEmpty());
    }

    @Test
    void testAddItem() {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem item = new CartItemBuilder()
                .withProductId("1234567")
                .withName("Product Name")
                .withQuantity(2)
                .withPrice(10.0)
                .build();

        shoppingCart.addItem(item);

        Map<String, CartItem> cartItemMap = shoppingCart.getCartItemMap();
        assertFalse(cartItemMap.isEmpty());
        assertTrue(cartItemMap.containsKey("1234567"));
        assertEquals(item, cartItemMap.get("1234567"));
    }

    @Test
    void testDeleteItem() {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem item = new CartItemBuilder()
                .withProductId("1234567")
                .withName("Product Name")
                .withQuantity(2)
                .withPrice(10.0)
                .build();

        shoppingCart.addItem(item);
        assertTrue(shoppingCart.getCartItemMap().containsKey("1234567"));

        shoppingCart.deleteItem(item);
        assertFalse(shoppingCart.getCartItemMap().containsKey("1234567"));
    }

    @Test
    void testAddNullItem() {
        ShoppingCart shoppingCart = new ShoppingCart();
        assertThrows(NullPointerException.class, () -> shoppingCart.addItem(null));
    }

    @Test
    void testDeleteNonExistentItem() {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem item = new CartItemBuilder()
                .withProductId("1234567")
                .withName("Product Name")
                .withQuantity(2)
                .withPrice(10.0)
                .build();

        assertDoesNotThrow(() -> shoppingCart.deleteItem(item));
    }

    @Test
    void testEditItem() {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem item = new CartItemBuilder()
                .withProductId("1234567")
                .withName("Product Name")
                .withQuantity(2)
                .withPrice(10.0)
                .build();

        shoppingCart.addItem(item);

        CartItem editedItem = new CartItemBuilder()
                .withProductId("1234567")
                .withName("Updated Product Name")
                .withQuantity(3)
                .withPrice(15.0)
                .build();

        shoppingCart.editItem(editedItem);
        assertEquals(editedItem, shoppingCart.getCartItemMap().get("1234567"));
    }

    @Test
    void testEditNonExistentItem() {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem item = new CartItemBuilder()
                .withProductId("1234567")
                .withName("Product Name")
                .withQuantity(2)
                .withPrice(10.0)
                .build();

        assertThrows(IllegalArgumentException.class, () -> shoppingCart.editItem(item));
    }
}
