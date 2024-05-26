package id.ac.ui.cs.advprog.cart.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ShoppingCartTest {
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCartBuilder().build();
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

        shoppingCart.getCartItemMap().put(productId, item);

        assertEquals(1, shoppingCart.getCartItemMap().size());
        assertEquals(item, shoppingCart.getCartItemMap().get(productId));
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

        shoppingCart.getCartItemMap().put(productId, item);

        assertEquals(1, shoppingCart.getCartItemMap().size());

        shoppingCart.getCartItemMap().remove(productId);

        assertEquals(0, shoppingCart.getCartItemMap().size());
        assertNull(shoppingCart.getCartItemMap().get(productId));
    }

    @Test
    void testAddMultipleItemsToCart() {
        CartItem item1 = new CartItemBuilder()
                .withProductId("P123")
                .withName("Test Product 1")
                .withQuantity(2)
                .withPrice(10.99)
                .build();

        CartItem item2 = new CartItemBuilder()
                .withProductId("P456")
                .withName("Test Product 2")
                .withQuantity(3)
                .withPrice(15.99)
                .build();

        shoppingCart.getCartItemMap().put(item1.getProductId(), item1);
        shoppingCart.getCartItemMap().put(item2.getProductId(), item2);

        assertEquals(2, shoppingCart.getCartItemMap().size());
        assertEquals(item1, shoppingCart.getCartItemMap().get(item1.getProductId()));
        assertEquals(item2, shoppingCart.getCartItemMap().get(item2.getProductId()));
    }

    @Test
    void testRemoveNonexistentItemFromCart() {
        assertEquals(0, shoppingCart.getCartItemMap().size());

        // Attempt to remove an item that doesn't exist
        shoppingCart.getCartItemMap().remove("NonexistentProductId");

        assertEquals(0, shoppingCart.getCartItemMap().size());
    }

    @Test
    void testCalculateTotalPriceWithoutVoucher() {
        CartItem item1 = new CartItemBuilder()
                .withProductId("P123")
                .withName("Test Product 1")
                .withQuantity(2)
                .withPrice(10.99)
                .build();

        CartItem item2 = new CartItemBuilder()
                .withProductId("P456")
                .withName("Test Product 2")
                .withQuantity(3)
                .withPrice(15.99)
                .build();

        shoppingCart.getCartItemMap().put(item1.getProductId(), item1);
        shoppingCart.getCartItemMap().put(item2.getProductId(), item2);

        assertEquals(2, shoppingCart.getCartItemMap().size());

        // Total price without voucher
        double totalPrice = shoppingCart.calculateTotalPrice(null);
        assertEquals(2 * 10.99 + 3 * 15.99, totalPrice);
    }

    @Test
    void testCalculateTotalPriceWithVoucher() {
        CartItem item1 = new CartItemBuilder()
                .withProductId("P123")
                .withName("Test Product 1")
                .withQuantity(2)
                .withPrice(10.99)
                .build();

        CartItem item2 = new CartItemBuilder()
                .withProductId("P456")
                .withName("Test Product 2")
                .withQuantity(3)
                .withPrice(15.99)
                .build();

        shoppingCart.getCartItemMap().put(item1.getProductId(), item1);
        shoppingCart.getCartItemMap().put(item2.getProductId(), item2);

        assertEquals(2, shoppingCart.getCartItemMap().size());

        double totalPrice = shoppingCart.calculateTotalPrice("VOUCHERCODE");
    }
}
