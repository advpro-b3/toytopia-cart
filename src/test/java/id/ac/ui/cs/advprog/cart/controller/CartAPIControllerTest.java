package id.ac.ui.cs.advprog.cart.controller;

import id.ac.ui.cs.advprog.cart.model.CartItem;
import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.service.ShoppingCartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartAPIController.class)
public class CartAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private CartAPIController cartAPIController;

    @Autowired
    private ObjectMapper objectMapper;

    private ShoppingCart cart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        cart = new ShoppingCart();
        cart.setUserId(1L);
        cart.setCartItemMap(new HashMap<>());
    }

    @Test
    public void testGetAllCarts() throws Exception {
        List<ShoppingCart> allCarts = Collections.singletonList(cart);
        when(shoppingCartService.getAllCarts()).thenReturn(allCarts);

        mockMvc.perform(get("/api/cart/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully retrieved all shopping carts"))
                .andExpect(jsonPath("$.carts").isArray());
    }

    @Test
    public void testGetShoppingCartInformation() throws Exception {
        when(shoppingCartService.getShoppingCartInformation(1L)).thenReturn(cart);

        mockMvc.perform(get("/api/cart/data/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully retrieved shopping cart data"))
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    public void testDeleteCartItem() throws Exception {
        mockMvc.perform(delete("/api/cart/delete/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item deleted successfully"));
    }

    @Test
    public void testDeleteAllCartItem() throws Exception {
        when(shoppingCartService.getShoppingCartInformation(1L)).thenReturn(cart);

        mockMvc.perform(delete("/api/cart/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All items deleted successfully"));
    }

    @Test
    public void testUpdateCartItem() throws Exception {
        CartItem item = new CartItem(1L, "1", "Product Name", 5, 10.0);
        cart.getCartItemMap().put("1", item);
        when(shoppingCartService.getShoppingCartInformation(1L)).thenReturn(cart);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", "1");
        requestBody.put("quantity", 10);

        mockMvc.perform(put("/api/cart/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item updated successfully"))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testUpdateAllCartItems() throws Exception {
        CartItem item1 = new CartItem(1L, "1", "Pikachu", 11, 2.0);
        CartItem item2 = new CartItem(2L, "2", "Charizard", 10, 10.0);
        cart.getCartItemMap().put("1", item1);
        cart.getCartItemMap().put("2", item2);
        when(shoppingCartService.getShoppingCartInformation(1L)).thenReturn(cart);

        Map<String, Object> cartItemMap = new HashMap<>();
        Map<String, Object> item1Map = new HashMap<>();
        item1Map.put("productId", "1");
        item1Map.put("quantity", 20);
        Map<String, Object> item2Map = new HashMap<>();
        item2Map.put("productId", "2");
        item2Map.put("quantity", 30);
        cartItemMap.put("1", item1Map);
        cartItemMap.put("2", item2Map);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("cartItemMap", cartItemMap);

        mockMvc.perform(put("/api/cart/updateAll/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All items updated successfully"));
    }

    @Test
    public void testAddItemToCart() throws Exception {
        when(shoppingCartService.getShoppingCartInformation(1L)).thenReturn(cart);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", "1");
        requestBody.put("name", "Pikachu");
        requestBody.put("quantity", 5);
        requestBody.put("price", 2.0);

        mockMvc.perform(put("/api/cart/addItem/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item added to cart successfully"));
    }
}
