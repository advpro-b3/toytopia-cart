package id.ac.ui.cs.advprog.cart.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ShoppingCartTest {
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    void testAddItemToCart() {
        Long id = 1L;
        String productId = "P123";
        String name = "Test Product";
        int quantity = 2;
        double price = 10.99;

        CartItem item = new CartItemBuilder()
                .withId(id)
                .withProductId(productId)
                .withName(name)
                .withQuantity(quantity)
                .withPrice(price)
                .build();

        shoppingCart.getCartItemMap().put("123", item);

        assertEquals(1, shoppingCart.getCartItemMap().size());
        assertEquals(item, shoppingCart.getCartItemMap().get("123"));
    }

    @Test
    void testRemoveItemFromCart() {
        Long id = 1L;
        String productId = "P123";
        String name = "Test Product";
        int quantity = 2;
        double price = 10.99;

        CartItem item = new CartItemBuilder()
                .withId(id)
                .withProductId(productId)
                .withName(name)
                .withQuantity(quantity)
                .withPrice(price)
                .build();

        shoppingCart.getCartItemMap().put("123", item);

        assertEquals(1, shoppingCart.getCartItemMap().size());

        shoppingCart.getCartItemMap().remove("123");

        assertEquals(0, shoppingCart.getCartItemMap().size());
        assertNull(shoppingCart.getCartItemMap().get("123"));
    }

}
