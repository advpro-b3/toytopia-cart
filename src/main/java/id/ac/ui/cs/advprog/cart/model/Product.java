package id.ac.ui.cs.advprog.cart.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private int discount;
    private String availability;

    public static class ProductBuilder {
        private String id;
        private String name;
        private String description;
        private int price;
        private int stock;
        private int discount;
        private String availability;

        public ProductBuilder(String name) {
            this.name = name;
        }

        public ProductBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder setPrice(int price) {
            this.price = price;
            return this;
        }

        public ProductBuilder setStock(int stock) {
            this.stock = stock;
            return this;
        }

        public ProductBuilder setDiscount(int discount) {
            this.discount = discount;
            return this;
        }

        public ProductBuilder setAvailability(String availability) {
            this.availability = availability;
            return this;
        }

        public Product build() {
            Product product = new Product();
            if (product.getId() == null) {
                UUID uuid = UUID.randomUUID();
                product.setId(uuid.toString());
            }
            product.setName(this.name);
            product.setDescription(this.description);
            product.setPrice(this.price);
            product.setStock(this.stock);
            product.setDiscount(this.discount);
            product.setAvailability(this.availability);
            return product;
        }
    }

    public boolean isValid() {
        if (this.name == null || this.name.isEmpty() || this.name.length() > 255) {
            return false;
        }
        if (this.description == null || this.description.isEmpty() || this.description.length() > 255){
            return false;
        }
        if (this.price <= 0) {
            return false;
        }
        if (this.stock < 0) {
            return false;
        }
        if (this.discount < 0 || this.discount > 100) {
            return false;
        }
        if (this.availability == null || this.availability.isEmpty()) {
            return false;
        }
        return true;
    }
}