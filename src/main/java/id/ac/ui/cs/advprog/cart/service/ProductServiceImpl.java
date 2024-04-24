package id.ac.ui.cs.advprog.cart.service;


import id.ac.ui.cs.advprog.cart.model.Product;
import id.ac.ui.cs.advprog.cart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product update(String productId, Product updatedProduct) {
        productRepository.update(productId, updatedProduct);
        return updatedProduct;
    }

    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(productId);
    }
}
