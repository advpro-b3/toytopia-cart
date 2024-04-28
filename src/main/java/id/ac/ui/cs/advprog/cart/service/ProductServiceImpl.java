package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.model.Product;

import id.ac.ui.cs.advprog.cart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findProductById(productId);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(String productId, Product updatedProduct) {
        Product product = productRepository.findProductById(productId);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        product.setDiscount(updatedProduct.getDiscount());
        product.setAvailability(updatedProduct.getAvailability());
        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(productId);
    }
}