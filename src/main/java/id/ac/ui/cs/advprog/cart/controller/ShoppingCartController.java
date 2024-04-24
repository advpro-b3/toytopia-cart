package id.ac.ui.cs.advprog.cart.controller;

import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
import id.ac.ui.cs.advprog.cart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoppingCart>> getAll() {
        List<ShoppingCart> all = shoppingCartService.findAll();
        if (all != null) {
            return ResponseEntity.ok(all);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cart-{userId}")
    public ResponseEntity<ShoppingCart> getByUserId(@PathVariable(name = "userId") Long userId) {
        ShoppingCart shoppingCart = shoppingCartService.findById(userId);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ShoppingCart> create(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart created = shoppingCartService.create(shoppingCart);
        if (created != null) {
            return ResponseEntity.ok(created);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<ShoppingCart> edit(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart edited = shoppingCartService.edit(shoppingCart);
        if (edited != null) {
            return ResponseEntity.ok(edited);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ShoppingCart> delete(@PathVariable(name = "userId") Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        shoppingCartService.delete(shoppingCart);
        return ResponseEntity.ok().build();
    }
}
