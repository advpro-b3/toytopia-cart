package id.ac.ui.cs.advprog.cart.model;

import id.ac.ui.cs.advprog.cart.service.VoucherService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Setter
@Getter
@Entity
public class ShoppingCart {
    @Id
    private Long userId;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "productId")
    private Map<String, CartItem> cartItemMap;
    @Transient
    private VoucherService voucherService;

    public ShoppingCart() {
        this.cartItemMap = new HashMap<>();
    }

    public void addItem(CartItem item) {
        cartItemMap.put(item.getProductId(), item);
    }

    public void removeItem(String productId) {
        cartItemMap.remove(productId);
    }

    public double calculateTotalPrice(String voucherCode) {
        double total = cartItemMap.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        if (voucherCode != null && !voucherCode.isEmpty()) {
            Map<String, Object> voucher = voucherService.getVoucherByCode(voucherCode);
            if (voucher != null && !voucher.isEmpty()) {
                Double discount = (Double) voucher.get("discount");
                Double maxDiscount = (Double) voucher.get("maxDiscount");
                Double minPurchase = (Double) voucher.get("minPurchase");

                if (total >= minPurchase) {
                    double discountAmount = total * discount / 100;
                    discountAmount = Math.min(discountAmount, maxDiscount);
                    total -= discountAmount;
                }
            }
        }

        return total;
    }

    public Map<String, CartItem> getAllCartItem() {
        return cartItemMap;
    }

    public CartItem addItemToCart(CartItem item) {
        return cartItemMap.put(item.getProductId(), item);
    }


}
