package id.ac.ui.cs.advprog.cart.model;

import id.ac.ui.cs.advprog.cart.service.VoucherService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.web.client.RestTemplate;

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
        this.voucherService = new VoucherService(new RestTemplate());
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

                if (minPurchase == null) {
                    minPurchase = 0.0;
                }
                if (maxDiscount == null) {
                    maxDiscount = Double.MAX_VALUE;
                }

                if (total >= minPurchase) {
                    double discountAmount = total * discount / 100;
                    discountAmount = Math.min(discountAmount, maxDiscount);
                    total -= discountAmount;
                }
            }
        }

        return total;
    }



}
