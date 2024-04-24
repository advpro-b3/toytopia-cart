package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceImplTest {

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    private List<ShoppingCart> shoppingCarts;

    @BeforeEach
    void setUp() {
        shoppingCarts = new ArrayList<>();

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setUserId(1L);
        shoppingCarts.add(cart1);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setUserId(2L);
        shoppingCarts.add(cart2);
    }

    @Test
    void testCreateShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(1L);

        when(shoppingCartRepository.create(shoppingCart)).thenReturn(shoppingCart);

        ShoppingCart createdShoppingCart = shoppingCartService.create(shoppingCart);

        assertNotNull(createdShoppingCart);
        assertEquals(shoppingCart.getUserId(), createdShoppingCart.getUserId());

        verify(shoppingCartRepository, times(1)).create(shoppingCart);
    }

    @Test
    void testFindShoppingCartById() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(1L);

        when(shoppingCartRepository.findById(1L)).thenReturn(shoppingCart);

        ShoppingCart foundShoppingCart = shoppingCartService.findById(1L);

        assertNotNull(foundShoppingCart);
        assertEquals(shoppingCart.getUserId(), foundShoppingCart.getUserId());

        verify(shoppingCartRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllShoppingCarts() {
        when(shoppingCartRepository.findAll()).thenReturn(shoppingCarts);

        List<ShoppingCart> foundShoppingCarts = shoppingCartService.findAll();

        assertNotNull(foundShoppingCarts);
        assertEquals(2, foundShoppingCarts.size());

        verify(shoppingCartRepository, times(1)).findAll();
    }

    @Test
    void testDeleteShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(1L);

        shoppingCartService.delete(shoppingCart);

        verify(shoppingCartRepository, times(1)).deleteById(shoppingCart.getUserId());
    }
}
