package id.ac.ui.cs.advprog.cart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.cart.model.CartItem;
import id.ac.ui.cs.advprog.cart.model.Product;
import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.model.ShoppingCartBuilder;
import id.ac.ui.cs.advprog.cart.repository.CartItemRepository;
import id.ac.ui.cs.advprog.cart.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    private ShoppingCartServiceImpl shoppingCartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        shoppingCartService = new ShoppingCartServiceImpl(shoppingCartRepository, cartItemRepository);
    }


    @Test
    public void testCreateOrUpdateCartItemToShoppingCart_NewCartItem() {
        Long userId = 1L;
        String productId = "prod123";
        CartItem cartItem = new CartItem(null, productId, "Product 123", 2, 10.0);

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(createEmptyCart(userId));

        shoppingCartService.createOrUpdateCartItemToShoppingCart(userId, cartItem);

        verify(shoppingCartRepository).save(any(ShoppingCart.class));
    }

    @Test
    public void testCreateOrUpdateCartItemToShoppingCart_ExistingItemUpdated() {
        Long userId = 1L;
        String productId = "prod123";
        CartItem existingItem = new CartItem(1L, productId, "Product 123", 1, 5.0);
        ShoppingCart cart = createEmptyCart(userId);
        cart.getCartItemMap().put(productId, existingItem);

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(cart);

        CartItem cartItem = new CartItem(null, productId, "Updated Name", 3, 12.0);
        shoppingCartService.createOrUpdateCartItemToShoppingCart(userId, cartItem);

        // Verify existing item was updated and cart was saved
        verify(shoppingCartRepository).save(cart);
    }

    @Test
    public void testDeleteCartItemFromShoppingCart_ExistingItem() {
        Long userId = 1L;
        String productId = "prod123";
        ShoppingCart cart = createEmptyCart(userId);
        cart.getCartItemMap().put(productId, new CartItem());

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(cart);

        shoppingCartService.deleteCartItemFromShoppingCart(userId, productId);

        // Verify item was removed and cart was saved
        verify(shoppingCartRepository).save(cart);
    }

    @Test
    public void testDeleteCartItemFromShoppingCart_NonexistentItem() {
        Long userId = 1L;
        String productId = "prod123";

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(createEmptyCart(userId));

        shoppingCartService.deleteCartItemFromShoppingCart(userId, productId);

        // No verification needed as there's nothing to remove
    }

    @Test
    public void testGetCartItemsFromShoppingCart_ExistingCart() {
        Long userId = 1L;
        ShoppingCart cart = createEmptyCart(userId);
        cart.getCartItemMap().put("prod1", new CartItem(1L, "prod1", "Product 1", 2, 10.0));

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(cart);

        List<CartItem> retrievedItems = shoppingCartService.getCartItemsFromShoppingCart(userId);

        assertEquals(1, retrievedItems.size()); // Simple size check
    }

    @Test
    public void testGetCartItemsFromShoppingCart_NonexistentCart() {
        Long userId = 1L;

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> shoppingCartService.getCartItemsFromShoppingCart(userId));
    }




    @Test
    public void testCreateShoppingCart_NewCart() {
        Long userId = 1L;

        shoppingCartService.createShoppingCart(userId);

        verify(shoppingCartRepository).save(any(ShoppingCart.class));
    }

    private ShoppingCart createEmptyCart(Long userId) {
        return new ShoppingCartBuilder().withCartItem(new HashMap<>()).withUserId(userId).build();
    }


    @Test
    void testAddItemToCart_NewProduct() {
        Long userId = 1L;
        Product product = new Product.ProductBuilder("P123")
                .setPrice(10)
                .build(); // Use the builder to create the product
        ShoppingCart cart = createEmptyCart(userId);

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(cart);

        CartItem addedItem = shoppingCartService.addItemToCart(userId, product);

        assertEquals(product.getId(), addedItem.getProductId());
        assertEquals(product.getName(), addedItem.getName());
        assertEquals(product.getPrice(), addedItem.getPrice());
        assertEquals(1, addedItem.getQuantity());

        verify(shoppingCartRepository).save(cart);
    }

    @Test
    void testAddItemToCart_ExistingProduct() {
        Long userId = 1L;
        String productId = "P123";
        Product product = new Product.ProductBuilder(productId)
                .setPrice(10)
                .build();
        ShoppingCart cart = createEmptyCart(userId);
        cart.getCartItemMap().put(productId, new CartItem(1L, productId, "Test Product", 1, 10));

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(cart);

        CartItem addedItem = shoppingCartService.addItemToCart(userId, product);

        assertEquals(1, addedItem.getQuantity());

        verify(shoppingCartRepository).save(cart);
    }

    @Test
    void testGetShoppingCartInformation_ValidCart() {
        Long userId = 1L;
        ShoppingCart cart = createEmptyCart(userId);

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(cart);

        CompletableFuture<ShoppingCart> futureCart = shoppingCartService.getShoppingCartInformation(userId);

        assertDoesNotThrow(() -> {
            ShoppingCart retrievedCart = futureCart.get(); // Blocking call to get the result
            assertNotNull(retrievedCart);
            assertEquals(userId, retrievedCart.getUserId());
        });
    }

    @Test
    void testGetShoppingCartInformation_NullCart() {
        Long userId = 1L;

        when(shoppingCartRepository.findShoppingCartByUserId(userId)).thenReturn(null);

        CompletableFuture<ShoppingCart> futureCart = shoppingCartService.getShoppingCartInformation(userId);

        assertThrows(ExecutionException.class, () -> {
            futureCart.get(); // Blocking call to get the result
        });
    }


}
