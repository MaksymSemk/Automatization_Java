package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Backend {
    private static final Map<String, String> products = new HashMap<>(); // Product ID -> Product Name
    private static int productIdCounter = 1;

    static {
        // Initial products
        products.put("P001", "Laptop - XPS 15");
        products.put("P002", "Smartphone - Galaxy S24");
        products.put("P003", "Headphones - Sony WH-1000XM5");
    }

    public static List<String> getAllProducts(String authToken) {
        if (AuthModule.isValid(authToken)) {
            System.out.println("Backend: Access denied. Invalid token.");
            return null;
        }
        System.out.println("Backend: Fetching all products.");
        return new ArrayList<>(products.values());
    }

    public static String addProduct(String authToken, String productName) {
        String username = AuthModule.getUsername(authToken);
        if (username == null) {
            System.out.println("Backend: Access denied. Invalid token.");
            return null;
        }
        // For simplicity, only allow "admin" to add products
        if (!username.equals("admin")) { // Simulating an admin role check
             System.out.println("Backend: Permission denied. Only admin can add products.");
             return null;
        }

        String newProductId = "P" + String.format("%03d", productIdCounter++);
        products.put(newProductId, productName);
        System.out.println("Backend: Product '" + productName + "' added with ID " + newProductId + " by " + username);
        return newProductId;
    }

    public static boolean placeOrder(String authToken, List<String> productIds) {
        if (AuthModule.isValid(authToken)) {
            System.out.println("Backend: Order placement failed. Invalid token.");
            return false;
        }
        if (productIds == null || productIds.isEmpty()) {
            System.out.println("Backend: Order placement failed. No items in order.");
            return false;
        }
        String username = AuthModule.getUsername(authToken);
        System.out.println("Backend: User '" + username + "' placed an order for " + productIds.size() + " items.");
        // In a real app, this would involve database transactions, inventory updates, etc.
        return true;
    }
}