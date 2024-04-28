package id.ac.ui.cs.advprog.cart.repository;

import id.ac.ui.cs.advprog.cart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
    Product findProductById(String productId);
}