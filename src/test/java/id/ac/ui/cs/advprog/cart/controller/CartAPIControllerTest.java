package id.ac.ui.cs.advprog.cart.controller;


import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.service.APIProductService;
import id.ac.ui.cs.advprog.cart.service.ShoppingCartService;
import id.ac.ui.cs.advprog.cart.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartAPIController.class)
public class CartAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private UserService userService;

    @MockBean
    private APIProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private CartAPIController cartAPIController;

    private ShoppingCart cart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

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
    public void testDeleteCartItem() throws Exception {
        mockMvc.perform(delete("/api/cart/delete/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item deleted successfully"));
    }


}
