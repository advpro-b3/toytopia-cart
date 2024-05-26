package id.ac.ui.cs.advprog.cart.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartItemTest {
    @Test
    public void testCalculateTotalPrice() {

        CartItem cartItem = new CartItem();
        cartItem.setProductId("1234567890");
        cartItem.setQuantity(10);
        cartItem.setPrice(1000);
        double totalPrice = cartItem.calculateTotalPrice();
        assertEquals(10000, totalPrice);
    }

    @Test
    public void testSetNegativeQuantity() {
        CartItem cartItem = new CartItem();
        assertThrows(IllegalArgumentException.class, () -> {
            cartItem.setQuantity(-1);
        });
    }

    @Test
    public void testSetZeroQuantity() {
        CartItem cartItem = new CartItem();
        assertThrows(IllegalArgumentException.class, () -> {
            cartItem.setQuantity(0);
        });
    }

    @Test
    public void testCartItemBuilder() {
        Long id = 1L;
        String productId = "P123";
        String name = "Test Product";
        int quantity = 2;
        double price = 10.99;

        CartItem cartItem = new CartItemBuilder()
                .withId(id)
                .withProductId(productId)
                .withName(name)
                .withQuantity(quantity)
                .withPrice(price)
                .build();

        // Verify that the CartItem is constructed correctly
        assertEquals(id, cartItem.getId());
        assertEquals(productId, cartItem.getProductId());
        assertEquals(name, cartItem.getName());
        assertEquals(quantity, cartItem.getQuantity());
        assertEquals(price, cartItem.getPrice(), 0.001); // Use delta for double comparison
    }


    @Test
    public void testCartItemBuilderSetZeroQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CartItemBuilder().withQuantity(0).build();
        });
    }

    @Test
    public void testCartItemBuilderWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CartItemBuilder().withPrice(-10.99).build();
        });
    }

    @Test
    public void testCartItemBuilderWithNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CartItemBuilder().withQuantity(-2).build();
        });
    }
}
