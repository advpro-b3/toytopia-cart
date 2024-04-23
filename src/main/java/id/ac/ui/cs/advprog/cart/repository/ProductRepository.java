package id.ac.ui.cs.advprog.cart.repository;

import id.ac.ui.cs.advprog.cart.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {

    List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getId() == null) {
            UUID uuid = UUID.randomUUID();
            product.setId(uuid.toString());
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        for (Product product : productData) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product update(String productId, Product updatedProduct) {
        for (Product product : productData) {
            if (product.getId().equals(productId)) {
                product.setName(updatedProduct.getName());
                product.setDescription(updatedProduct.getDescription());
                product.setPrice(updatedProduct.getPrice());
                product.setStock(updatedProduct.getStock());
                product.setDiscount(updatedProduct.getDiscount());
                product.setAvailability(updatedProduct.getAvailability());
                return product;
            }
        }
        return null;
    }

    public void deleteById(String productId) {
        productData.removeIf(product -> product.getId().equals(productId));
    }
}