package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product findById(String productId);
    List<Product> findAll();
    Product update(String productId, Product updatedProduct);
    void deleteById(String productId);
}
