//controller for cart
package com.example.customerui;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private List<OrderItem> cart = new ArrayList<>();

    @GetMapping
    public List<OrderItem> getCart() {
        return cart;
    }

    @PostMapping
    public OrderItem addItemToCart(@RequestBody OrderItem item) {
        Optional<OrderItem> existingItem = cart.stream()
            .filter(orderItem -> orderItem.getProductID().equals(item.getProductID()))
            .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + 1);
        } else {
            cart.add(item);
        }
        return item;
    }

    @DeleteMapping
    public void clearCart() {
        cart.clear();
    }

    @DeleteMapping("/{id}")
    public void removeItemFromCart(@PathVariable String id) {
        cart.removeIf(item -> item.getProductID().equals(id));
    }
}


