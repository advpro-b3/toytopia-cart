package id.ac.ui.cs.advprog.cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private ShoppingCart savedCart;

    @BeforeEach
    public void setUp() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(1L);
        savedCart = shoppingCartRepository.save(cart);
    }

    @Test
    public void testFindShoppingCartByUserId_ExistingCart() {
        ShoppingCart retrievedCart = shoppingCartRepository.findShoppingCartByUserId(savedCart.getUserId());
        assertThat(retrievedCart)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(savedCart);
    }

    @Test
    public void testFindShoppingCartByUserId_NonexistentCart() {
        assertThat(shoppingCartRepository.findShoppingCartByUserId(10L)).isNull();
    }
}
