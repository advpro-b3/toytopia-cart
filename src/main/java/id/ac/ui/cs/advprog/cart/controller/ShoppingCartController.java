//package id.ac.ui.cs.advprog.cart.controller;
//
//import id.ac.ui.cs.advprog.cart.model.ShoppingCart;
//import id.ac.ui.cs.advprog.cart.service.ShoppingCartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/cart")
//public class ShoppingCartController {
//    @Autowired
//    private ShoppingCartService shoppingCartService;
//
//    @GetMapping("/create")
//    public String createShoppingCartPage(Model model) {
//        ShoppingCart shoppingCart = new ShoppingCart();
//        model.addAttribute("shoppingCart", shoppingCart);
//        return "CreateShoppingCart";
//    }
//
//    @PostMapping("/create")
//    public String createShoppingCartPost(@ModelAttribute ShoppingCart shoppingCart, Model model) {
//        shoppingCartService.create(shoppingCart);
//        return "redirect:list";
//    }
//
//    @GetMapping("/list")
//    public String shoppingCartListPage(Model model) {
//        List<ShoppingCart> allShoppingCarts = shoppingCartService.findAll();
//        model.addAttribute("shoppingCarts", allShoppingCarts);
//        return "ShoppingCartList";
//    }
//
//    @GetMapping("/edit/{userId}")
//    public String editShoppingCartPage(@PathVariable("userId") Long userId, Model model) {
//        ShoppingCart shoppingCart = shoppingCartService.findById(userId);
//        model.addAttribute("shoppingCart", shoppingCart);
//        return "EditShoppingCart";
//    }
//
//    @PostMapping("/edit")
//    public String editShoppingCartPost(@ModelAttribute ShoppingCart shoppingCart, Model model) {
//        shoppingCartService.edit(shoppingCart);
//        return "redirect:list";
//    }
//
//    @GetMapping("/delete/{userId}")
//    public String deleteShoppingCart(@PathVariable("userId") Long userId, Model model) {
//        ShoppingCart shoppingCart = shoppingCartService.findById(userId);
//        shoppingCartService.delete(shoppingCart);
//        return "redirect:../list";
//    }
//}
