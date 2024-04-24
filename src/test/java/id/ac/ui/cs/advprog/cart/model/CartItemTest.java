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
}
