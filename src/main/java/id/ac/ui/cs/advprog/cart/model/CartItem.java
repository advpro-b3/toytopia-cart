package id.ac.ui.cs.advprog.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartItem {
    @Id
    private Long id;
    private String productId;
    private String name;
    private int quantity;
    private double price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ShoppingCart shoppingCart;

    public CartItem(){}
    public CartItem(Long id, String productId, String name, int quantity, double price){
        this.id = id;
        this.productId = productId;
        this.name = name;
        setPrice(price);
        setQuantity(quantity);
    }

    public void setPrice(double price) {
        validatePrice(price);
        this.price = price;
    }

    public void validatePrice(double price){
        if(price<=0) throw new IllegalArgumentException("Harga tidak boleh negatif");
    }

    public void setQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }
    public void validateQuantity(int quantity){
        if(quantity <=0) throw new IllegalArgumentException("Cart Item tidak boleh kosong");
    }
    public double calculateTotalPrice() {
        return price * quantity;
    }

    public void updateValues(String name, double price, int quantity) {
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }



    public Long getCartItemId() {
        return id;
    }
}


