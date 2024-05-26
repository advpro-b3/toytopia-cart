package id.ac.ui.cs.advprog.cart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.cart.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class APIProductServiceTest {

    private RestTemplate restTemplate;
    private APIProductService productService;

    @BeforeEach
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        productService = new APIProductService(restTemplate);
    }

    @Test
    public void testGetAllProducts_Success() {
        // Mock response from the REST API
        Product[] productsArray = {
                new Product.ProductBuilder("Product 1").setPrice(10).build(),
                new Product.ProductBuilder("Product 2").setPrice(15).build()
        };

        when(restTemplate.getForObject(anyString(), eq(Product[].class))).thenReturn(productsArray);

        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product.ProductBuilder("Product 1").setPrice(10).build());
        expectedProducts.add(new Product.ProductBuilder("Product 2").setPrice(15).build());

        List<Product> actualProducts = productService.getAllProducts();

        assertEquals(expectedProducts.size(), actualProducts.size());
    }

    @Test
    public void testGetProductById_Success() {
        // Mock response from the REST API
        String productId = "P123";
        Product expectedProduct = new Product.ProductBuilder("Product 1").setPrice(10).build();

        when(restTemplate.getForObject(anyString(), eq(Product.class))).thenReturn(expectedProduct);

        Product actualProduct = productService.getProductById(productId);

        assertEquals(expectedProduct, actualProduct);
    }
}