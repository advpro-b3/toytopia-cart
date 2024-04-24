package id.ac.ui.cs.advprog.cart.repository;

import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ShoppingCartRepositoryTest {

    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCart mockShoppingCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        shoppingCartRepository = new ShoppingCartRepository();
    }

    @Test
    void testCreateShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(1L);

        ShoppingCart createdShoppingCart = shoppingCartRepository.create(shoppingCart);

        assertEquals(1, shoppingCartRepository.findAll().size());
        assertEquals(shoppingCart, createdShoppingCart);
    }

    @Test
    void testFindShoppingCartById() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(1L);

        shoppingCartRepository.create(shoppingCart);

        assertEquals(shoppingCart, shoppingCartRepository.findById(1L));
    }

    @Test
    void testFindShoppingCartByInvalidId() {
        assertNull(shoppingCartRepository.findById(999L));
    }

    @Test
    void testDeleteShoppingCartById() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(1L);

        shoppingCartRepository.create(shoppingCart);
        shoppingCartRepository.deleteById(1L);

        assertNull(shoppingCartRepository.findById(1L));
    }

    @Test
    void testFindAllShoppingCarts() {
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setUserId(1L);

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setUserId(2L);

        shoppingCartRepository.create(shoppingCart1);
        shoppingCartRepository.create(shoppingCart2);

        assertEquals(2, shoppingCartRepository.findAll().size());
    }
}
