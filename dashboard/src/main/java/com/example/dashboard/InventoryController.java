//controller for inventory
package com.example.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryDB inventoryDB;

    // Injecting InventoryDB using constructor
    @Autowired
    public InventoryController(InventoryDB inventoryDB) {
        this.inventoryDB = inventoryDB;
    }

    // Your endpoint to get inventory
    @GetMapping("/")
    public ResponseEntity<List<Inventory>> getInventory() {
        // Assuming you have a method in InventoryDB that gets all inventory items
        List<Inventory> inventoryList = inventoryDB.fetchAllInventory(); // Adjust this method if needed
        return ResponseEntity.ok(inventoryList);
    }

    // Your endpoint to update stock
    @PutMapping("/updateStock")
    public ResponseEntity<String> updateStock(
            @RequestParam String productID,
            @RequestParam int quantity) {
        inventoryDB.updateInventoryStock(productID, quantity);
        return ResponseEntity.ok("Stock updated successfully!");
    }

    // Your endpoint to update price
    @PutMapping("/updatePrice")
    public ResponseEntity<String> updatePrice(
            @RequestParam String productID,
            @RequestParam float price) {
        inventoryDB.updateProductPrice(productID, price);
        return ResponseEntity.ok("Price updated successfully!");
    }
}
