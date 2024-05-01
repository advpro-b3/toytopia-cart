package id.ac.ui.cs.advprog.cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import id.ac.ui.cs.advprog.cart.model.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    private CartItem savedCartItem;

    @BeforeEach
    public void setUp() {
        CartItem cartItem = new CartItem(1L, "product1", "Product 1", 2, 10.0);
        savedCartItem = cartItemRepository.save(cartItem);
    }

    @Test
    public void testFindCartItemById_ExistingItem() {
        CartItem retrievedItem = cartItemRepository.findCartItemById(savedCartItem.getId());
        assertThat(retrievedItem)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(savedCartItem);
    }

    @Test
    public void testFindCartItemById_NonexistentItem() {
        assertThat(cartItemRepository.findCartItemById(10L)).isNull();
    }
}
