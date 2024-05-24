package id.ac.ui.cs.advprog.cart.repository;

import id.ac.ui.cs.advprog.cart.enums.Availability;
import id.ac.ui.cs.advprog.cart.model.Product;
import id.ac.ui.cs.advprog.cart.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testFindByOwnerId() {
        Product.ProductBuilder productBuilder = new Product.ProductBuilder("Hot Wheels 18 Camaro SS")
                .setDescription("The  '18 Camaro SS is based on Hot Wheels' 50th Anniversary SEMA 2017 auto show in Las Vegas. A casting designed by Brendon Vetuskey with an initial-release color of Crush Orange. The Sixth Generation Camaro Hot Wheels Anniversary Special Edition was created by a team of designers led by Tom Peters.")
                .setPrice(25000)
                .setStock(20)
                .setDiscount(10)
                .setAvailability(Availability.READY.getValue());
        Product product = productBuilder.build();
        when(productRepository.findProductById(product.getId())).thenReturn(product);
        Product foundProduct = productService.findById(product.getId());

        verify(productRepository).findProductById(product.getId());

        assertEquals("Hot Wheels 18 Camaro SS", foundProduct.getName());
    }
}