package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FrontendCLI {
    private static String currentAuthToken = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the E-commerce CLI!");

        AuthModule.registerUser("admin", "adminpass");

        mainMenu();
    }

    private static void mainMenu() {
        int choice;
        do {
            System.out.println("\n--- Main Menu ---");
            if (currentAuthToken == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
            } else {
                System.out.println("3. View Products");
                System.out.println("4. Add Product (Admin Only)");
                System.out.println("5. Place Order");
                System.out.println("5. Place Order");
                System.out.println("6. Logout");
            }
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
            }

            if (currentAuthToken == null) {
                switch (choice) {
                    case 1: registerUser(); break;
                    case 2: loginUser(); break;
                    case 0: System.out.println("Exiting E-commerce CLI. Goodbye!"); break;
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } else {
                switch (choice) {
                    case 3: viewProducts(); break;
                    case 4: addProduct(); break;
                    case 5: placeOrder(); break;
                    case 6: logoutUser(); break;
                    case 0: System.out.println("Exiting E-commerce CLI. Goodbye!"); break;
                    default: System.out.println("Invalid choice. Please try again.");
                }
            }
        } while (choice != 0);
        scanner.close();
    }

    private static void registerUser() {
        System.out.print("Enter desired username: ");
        String username = scanner.nextLine();
        System.out.print("Enter desired password: ");
        String password = scanner.nextLine();
        AuthModule.registerUser(username, password);
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        currentAuthToken = AuthModule.login(username, password);
        if (currentAuthToken != null) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    private static void logoutUser() {
        if (currentAuthToken != null) {
            AuthModule.logout(currentAuthToken);
            currentAuthToken = null;
        } else {
            System.out.println("You are not logged in.");
        }
    }

    private static void viewProducts() {
        if (currentAuthToken == null) {
            System.out.println("Please log in to view products.");
            return;
        }
        System.out.println("\n--- Available Products ---");
        List<String> products = Backend.getAllProducts(currentAuthToken);
        if (products != null && !products.isEmpty()) {
            for (String product : products) {
                System.out.println("- " + product);
            }
        } else if (products != null) {
            System.out.println("No products available.");
        }
    }

    private static void addProduct() {
        if (currentAuthToken == null) {
            System.out.println("Please log in to add products.");
            return;
        }
        // Frontend doesn't need to know *how* Backend checks for admin, just passes token.
        // Backend handles the authorization logic.
        System.out.print("Enter product name to add: ");
        String productName = scanner.nextLine();
        String productId = Backend.addProduct(currentAuthToken, productName);
        if (productId != null) {
            System.out.println("Product '" + productName + "' added successfully with ID: " + productId);
        } else {
            System.out.println("Failed to add product. Check permissions or token.");
        }
    }

    private static void placeOrder() {
        if (currentAuthToken == null) {
            System.out.println("Please log in to place an order.");
            return;
        }
        System.out.println("\n--- Place Order ---");
        List<String> productsToOrder = new ArrayList<>();
        List<String> availableProducts = Backend.getAllProducts(currentAuthToken); // Get current product list

        if (availableProducts == null || availableProducts.isEmpty()) {
            System.out.println("No products available to order.");
            return;
        }

        System.out.println("Available products:");
        for (int i = 0; i < availableProducts.size(); i++) {
            System.out.println((i + 1) + ". " + availableProducts.get(i));
        }

        String input;
        do {
            System.out.print("Enter product number to add to cart (0 to finish): ");
            input = scanner.nextLine();
            try {
                int productNum = Integer.parseInt(input);
                if (productNum > 0 && productNum <= availableProducts.size()) {
                    // In a real scenario, you'd add actual product IDs, not names
                    // For simplicity, we'll just use the product name as an identifier here
                    productsToOrder.add("P00" + productNum); // Simulate ID based on order
                    System.out.println("Added '" + availableProducts.get(productNum - 1) + "' to cart.");
                } else if (productNum != 0) {
                    System.out.println("Invalid product number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (!input.equals("0"));

        if (productsToOrder.isEmpty()) {
            System.out.println("No items selected for order.");
            return;
        }

        if (Backend.placeOrder(currentAuthToken, productsToOrder)) {
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Failed to place order.");
        }
    }
}