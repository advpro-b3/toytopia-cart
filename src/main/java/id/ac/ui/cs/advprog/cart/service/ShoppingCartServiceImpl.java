package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartRepository.create(shoppingCart);
    }

    @Override
    public ShoppingCart edit(ShoppingCart shoppingCart) {
        ShoppingCart existingCart = shoppingCartRepository.findById(shoppingCart.getUserId());
        if (existingCart != null) {
            return shoppingCartRepository.update(shoppingCart.getUserId(), shoppingCart);
        }
        return null;
    }

    @Override
    public void delete(ShoppingCart shoppingCart) {
        shoppingCartRepository.deleteById(shoppingCart.getUserId());
    }

    @Override
    public ShoppingCart findById(Long userId) {
        return shoppingCartRepository.findById(userId);
    }

    @Override
    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }
}