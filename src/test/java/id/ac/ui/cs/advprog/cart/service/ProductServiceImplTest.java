package id.ac.ui.cs.advprog.cart.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import id.ac.ui.cs.advprog.cart.enums.Availability;
import id.ac.ui.cs.advprog.cart.model.Product;
import id.ac.ui.cs.advprog.cart.repository.ProductRepository;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    List<Product> products;

    @BeforeEach
    void setUp(){
        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("Hot Wheels 18 Camaro SS");
        product1.setDescription("The  '18 Camaro SS is based on Hot Wheels' 50th Anniversary SEMA 2017 auto show in Las Vegas. A casting designed by Brendon Vetuskey with an initial-release color of Crush Orange. The Sixth Generation Camaro Hot Wheels Anniversary Special Edition was created by a team of designers led by Tom Peters.");
        product1.setPrice(25000);
        product1.setStock(20);
        product1.setDiscount(10);
        product1.setAvailability(Availability.READY.getValue());
        products.add(product1);

        Product product2 = new Product();
        product2.setName("Hot Wheels Pajero SS");
        product2.setDescription("Pajero");
        product2.setPrice(25000);
        product2.setStock(20);
        product2.setDiscount(10);
        product2.setAvailability(Availability.READY.getValue());
        products.add(product2);
    }

    @Test
    void create() {
        Product product = products.getFirst();
        when(productRepository.create(product)).thenReturn(product);
        assertEquals(product, productService.create(product));
    }

    @Test
    void findById() {
        Product product = products.get(1);
        when(productRepository.findById(product.getId())).thenReturn(product);
        assertEquals(product, productService.findById(product.getId()));
    }

    @Test
    void findAll() {
        when(productRepository.findAll()).thenReturn(products.iterator());
        assertEquals(products.size(), productService.findAll().size());
    }

    @Test
    void update() {
        Product product = products.get(1);
        Product updatedProduct = new Product();
        updatedProduct.setName("Hot Wheels Becak SS");
        updatedProduct.setDescription("Becak");
        when(productRepository.update(product.getId(), updatedProduct)).thenReturn(updatedProduct);

        productService.update(product.getId(), updatedProduct);
        verify(productRepository, times(1)).update(product.getId(), updatedProduct);
    }
}
