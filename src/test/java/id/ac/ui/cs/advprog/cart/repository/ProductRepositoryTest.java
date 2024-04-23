package id.ac.ui.cs.advprog.cart.repository;


import id.ac.ui.cs.advprog.cart.enums.Availability;
import id.ac.ui.cs.advprog.cart.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createFindUpdate() {
        Product product = new Product();
        product.setName("Hot Wheels 18 Camaro SS");
        product.setDescription("The  '18 Camaro SS is based on Hot Wheels' 50th Anniversary SEMA 2017 auto show in Las Vegas. A casting designed by Brendon Vetuskey with an initial-release color of Crush Orange. The Sixth Generation Camaro Hot Wheels Anniversary Special Edition was created by a team of designers led by Tom Peters.");
        product.setPrice(25000);
        product.setStock(20);
        product.setDiscount(10);
        product.setAvailability(Availability.READY.getValue());
        Product createdProduct = productRepository.create(product);

        Iterator<Product> products = productRepository.findAll();
        assertTrue(products.hasNext());

        Product foundProduct = productRepository.findById(createdProduct.getId());
        assertEquals(createdProduct, foundProduct);

        Product updatedProduct = new Product();
        updatedProduct.setName("Hot Wheels Mustang GT");
        Product updated = productRepository.update(createdProduct.getId(), updatedProduct);
        assertEquals(updated.getName(), "Hot Wheels Mustang GT");
    }

    @Test
    void delete() {
        Product product = new Product();
        product.setName("Hot Wheels 18 Camaro SS");
        product.setDescription("The  '18 Camaro SS is based on Hot Wheels' 50th Anniversary SEMA 2017 auto show in Las Vegas. A casting designed by Brendon Vetuskey with an initial-release color of Crush Orange. The Sixth Generation Camaro Hot Wheels Anniversary Special Edition was created by a team of designers led by Tom Peters.");
        product.setPrice(25000);
        product.setStock(20);
        product.setDiscount(10);
        product.setAvailability(Availability.READY.getValue());
        Product createdProduct = productRepository.create(product);

        productRepository.deleteById(createdProduct.getId());
        Iterator<Product> products = productRepository.findAll();
        assertFalse(products.hasNext());
    }
}
