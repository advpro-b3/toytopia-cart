package id.ac.ui.cs.advprog.cart.model;

import id.ac.ui.cs.advprog.cart.enums.Availability;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {
    Product product;

    @BeforeEach
    void SetUp() {
        product = new Product();
        this.product.setId("1234567890");
        this.product.setName("Hot Wheels 18 Camaro SS");
        this.product.setDescription("The  '18 Camaro SS is based on Hot Wheels' 50th Anniversary SEMA 2017 auto show in Las Vegas. A casting designed by Brendon Vetuskey with an initial-release color of Crush Orange. The Sixth Generation Camaro Hot Wheels Anniversary Special Edition was created by a team of designers led by Tom Peters.");
        this.product.setPrice(25000);
        this.product.setStock(20);
        this.product.setDiscount(10);
        this.product.setAvailability(Availability.READY.getValue());
    }

    @Test
    void testGetId() {
        assertEquals("1234567890", this.product.getId());
    }

    @Test
    void testGetName() {
        assertEquals("Hot Wheels 18 Camaro SS", this.product.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("The  '18 Camaro SS is based on Hot Wheels' 50th Anniversary SEMA 2017 auto show in Las Vegas. A casting designed by Brendon Vetuskey with an initial-release color of Crush Orange. The Sixth Generation Camaro Hot Wheels Anniversary Special Edition was created by a team of designers led by Tom Peters.", this.product.getDescription());
    }

    @Test
    void testGetPrice() {
        assertEquals(25000, this.product.getPrice());
    }

    @Test
    void testGetStock() {
        assertEquals(20, this.product.getStock());
    }

    @Test
    void testGetDiscount() {
        assertEquals(10, this.product.getDiscount());
    }

    @Test
    void testGetAvailability() {
        assertEquals(Availability.READY.getValue(), this.product.getAvailability());
    }

    @Test
    void testSetAvailabilityToPreorder() {
        this.product.setAvailability(Availability.PREORDER.getValue());
        assertEquals(Availability.PREORDER.getValue(), this.product.getAvailability());
    }
}
