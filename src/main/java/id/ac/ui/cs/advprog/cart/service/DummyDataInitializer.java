package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.model.CartItem;
import id.ac.ui.cs.advprog.cart.model.CartItemBuilder;
import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DummyDataInitializer {

    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public DummyDataInitializer(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        initializeDummyData();
    }

    private void initializeDummyData() {
        // Creating dummy shopping carts
        ShoppingCart cart1 = new ShoppingCart();
        ShoppingCart cart2 = new ShoppingCart();
        ShoppingCart cart3 = new ShoppingCart();

        // Adding dummy cart items to the shopping carts
        cart1.addItem(new CartItemBuilder()
                .withId(1L)
                .withProductId("P123")
                .withName("Product 1")
                .withQuantity(2)
                .withPrice(10.99)
                .build());

        cart1.addItem(new CartItemBuilder()
                .withId(2L)
                .withProductId("P456")
                .withName("Product 2")
                .withQuantity(1)
                .withPrice(5.99)
                .build());

        cart2.addItem(new CartItemBuilder()
                .withId(3L)
                .withProductId("P789")
                .withName("Product 3")
                .withQuantity(3)
                .withPrice(8.50)
                .build());

        shoppingCartRepository.create(cart1);
        shoppingCartRepository.create(cart2);
        shoppingCartRepository.create(cart3);
    }
}

