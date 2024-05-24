package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.List;

@Service
public class APIProductService {
    private final String PRODUCTS_API_URL = "https://toytopia-product-feiujl7vfq-uc.a.run.app/api/product-service/all-products";

    private final RestTemplate restTemplate;

    @Autowired
    public APIProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getAllProducts() {
        try {
            Product[] productsArray = restTemplate.getForObject(PRODUCTS_API_URL, Product[].class);
            return Arrays.asList(productsArray);
        } catch (Exception e) {

            return null;
        }
    }
}
