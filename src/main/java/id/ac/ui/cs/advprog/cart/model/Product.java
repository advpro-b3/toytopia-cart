package id.ac.ui.cs.advprog.cart.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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
}