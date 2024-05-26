package id.ac.ui.cs.advprog.cart.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import id.ac.ui.cs.advprog.cart.dto.UserResponse;
import id.ac.ui.cs.advprog.cart.enums.Availability;
import id.ac.ui.cs.advprog.cart.model.CartItem;
import id.ac.ui.cs.advprog.cart.model.Product;
import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.service.APIProductService;
import id.ac.ui.cs.advprog.cart.service.ShoppingCartService;
import id.ac.ui.cs.advprog.cart.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CartAPIControllerTest {

    private ShoppingCartService shoppingCartService;
    private UserService userService;
    private APIProductService productService;
    private CartAPIController cartAPIController;

    @BeforeEach
    public void setUp() {
        shoppingCartService = mock(ShoppingCartService.class);
        userService = mock(UserService.class);
        productService = mock(APIProductService.class);
        cartAPIController = new CartAPIController();
        cartAPIController.shoppingCartService = shoppingCartService;
        cartAPIController.userService = userService;
        cartAPIController.productService = productService;
    }

    @Test
    public void testCreateShoppingCart_Success() throws ExecutionException, InterruptedException {
        String token = "dummyToken";
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        CompletableFuture<UserResponse> userFuture = CompletableFuture.completedFuture(userResponse);
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(1L);

        when(userService.getUsernameWithToken(token)).thenReturn(userFuture);
        when(shoppingCartService.createShoppingCart(userResponse.getId())).thenReturn(cart);

        ResponseEntity<Object> response = cartAPIController.createShoppingCart(token);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Shopping cart created successfully", responseBody.get("message"));
        assertEquals(1L, responseBody.get("userId"));
    }

    @Test
    public void testCreateShoppingCart_AlreadyExists() throws ExecutionException, InterruptedException {
        String token = "dummyToken";
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        CompletableFuture<UserResponse> userFuture = CompletableFuture.completedFuture(userResponse);

        when(userService.getUsernameWithToken(token)).thenReturn(userFuture);
        when(shoppingCartService.createShoppingCart(userResponse.getId())).thenReturn(null);

        ResponseEntity<Object> response = cartAPIController.createShoppingCart(token);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Shopping cart already exists for user", responseBody.get("message"));
        assertEquals(1L, responseBody.get("userId"));
    }

    @Test
    public void testCreateShoppingCart_Error() throws ExecutionException, InterruptedException {
        String token = "dummyToken";
        CompletableFuture<UserResponse> userFuture = CompletableFuture.failedFuture(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        when(userService.getUsernameWithToken(token)).thenReturn(userFuture);

        ResponseEntity<Object> response = cartAPIController.createShoppingCart(token);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Error occurred while retrieving user information", responseBody.get("message"));
    }

    @Test
    public void testGetAllCarts_Success() {
        List<ShoppingCart> allCarts = new ArrayList<>();
        allCarts.add(new ShoppingCart());

        when(shoppingCartService.getAllCarts()).thenReturn(allCarts);

        ResponseEntity<Object> response = cartAPIController.getAllCarts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Successfully retrieved all shopping carts", responseBody.get("message"));
        assertEquals(allCarts, responseBody.get("carts"));
    }

    @Test
    public void testGetShoppingCartInformation_Success() throws ExecutionException, InterruptedException {
        Long userId = 1L;
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(userId);
        CompletableFuture<ShoppingCart> cartFuture = CompletableFuture.completedFuture(cart);

        when(shoppingCartService.getShoppingCartInformation(userId)).thenReturn(cartFuture);

        CompletableFuture<ResponseEntity<Object>> responseFuture = cartAPIController.getShoppingCartInformation(userId, null);

        ResponseEntity<Object> response = responseFuture.get();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Successfully retrieved shopping cart data", responseBody.get("message"));
        assertEquals(userId, responseBody.get("userId"));
        assertEquals(cart.getCartItemMap(), responseBody.get("cartItems"));
    }

    @Test
    public void testGetShoppingCartInformation_NotFound() throws ExecutionException, InterruptedException {
        Long userId = 1L;
        CompletableFuture<ShoppingCart> cartFuture = CompletableFuture.completedFuture(null);

        when(shoppingCartService.getShoppingCartInformation(userId)).thenReturn(cartFuture);

        CompletableFuture<ResponseEntity<Object>> responseFuture = cartAPIController.getShoppingCartInformation(userId, null);

        ResponseEntity<Object> response = responseFuture.get();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Successfully retrieved shopping cart data", responseBody.get("message"));
        assertEquals(userId, responseBody.get("userId"));
        assertTrue(responseBody.get("cartItems") instanceof Map);
        assertTrue(((Map<?, ?>) responseBody.get("cartItems")).isEmpty());
    }

    @Test
    public void testDeleteCartItem_Success() {
        Long userId = 1L;
        String productId = "P123";

        doNothing().when(shoppingCartService).deleteCartItemFromShoppingCart(userId, productId);

        ResponseEntity<Object> response = cartAPIController.deleteCartItem(userId, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Item deleted successfully", responseBody.get("message"));
    }


    @Test
    public void testDeleteAllCartItems_NotFound() throws ExecutionException, InterruptedException {
        Long userId = 1L;
        CompletableFuture<ShoppingCart> cartFuture = CompletableFuture.completedFuture(null);

        when(shoppingCartService.getShoppingCartInformation(userId)).thenReturn(cartFuture);

        CompletableFuture<ResponseEntity<Object>> responseFuture = cartAPIController.deleteAllCartItem(userId);

        ResponseEntity<Object> response = responseFuture.get();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Shopping cart not found", responseBody.get("message"));
    }





    @Test
    public void testAddItemToCart_ProductNotFound() throws ExecutionException, InterruptedException {
        Long userId = 1L;
        String productId = "P123";

        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(userId);
        CompletableFuture<ShoppingCart> cartFuture = CompletableFuture.completedFuture(cart);

        when(shoppingCartService.getShoppingCartInformation(userId)).thenReturn(cartFuture);
        when(productService.getProductById(productId)).thenReturn(null);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        CompletableFuture<ResponseEntity<Object>> responseFuture = cartAPIController.addItemToCart(userId, requestBody);

        ResponseEntity<Object> response = responseFuture.get();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Product not found", responseBody.get("message"));
    }

    @Test
    public void testAddItemToCart_ShoppingCartNotFound() throws ExecutionException, InterruptedException {
        Long userId = 1L;
        String productId = "P123";
        Product.ProductBuilder productBuilder1 = new Product.ProductBuilder("Hot Wheels 18 Camaro SS");
        Product product = productBuilder1.setDescription("The  '18 Camaro SS is based on Hot Wheels' 50th Anniversary SEMA 2017 auto show in Las Vegas. A casting designed by Brendon Vetuskey with an initial-release color of Crush Orange. The Sixth Generation Camaro Hot Wheels Anniversary Special Edition was created by a team of designers led by Tom Peters.")
                .setPrice(25000)
                .setStock(20)
                .setDiscount(10)
                .setAvailability(Availability.READY.getValue())
                .build();

        CompletableFuture<ShoppingCart> cartFuture = CompletableFuture.completedFuture(null);

        when(shoppingCartService.getShoppingCartInformation(userId)).thenReturn(cartFuture);
        when(productService.getProductById(productId)).thenReturn(product);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        CompletableFuture<ResponseEntity<Object>> responseFuture = cartAPIController.addItemToCart(userId, requestBody);

        ResponseEntity<Object> response = responseFuture.get();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Shopping cart not found", responseBody.get("message"));
    }
}
