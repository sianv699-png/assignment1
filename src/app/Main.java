package app;

import project.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Product> products = new ArrayList<>();
    private static final ArrayList<Order> orders = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int nextProductId = 1;
    private static int nextOrderId = 1;

    public static void main(String[] args) {
        seedData();
        boolean running = true;
        while (running) {
            System.out.println("\n=== E-Commerce System ===");
            System.out.println("1. Employee menu");
            System.out.println("2. Customer menu");
            System.out.println("0. Exit");
            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> employeeMenu();
                case 2 -> customerMenu();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
        System.out.println("Goodbye!");
    }


    private static void seedData() {
        users.add(new Employee("Admin", "admin@shop.com", "admin", "Manager"));
        users.add(new Customer("Vika", "vika@mail.com", "1234", "Petropavl"));
        products.add(new Book(nextProductId++, "Clean Code", 25.0, 5, "Robert Martin"));
        products.add(new Electronics(nextProductId++, "Headphones", 40.0, 3, 12));
        products.add(new Clothing(nextProductId++, "T-Shirt", 15.0, 10, "M"));
    }


    private static void employeeMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Add product");
            System.out.println("2. List products");
            System.out.println("3. Add user");
            System.out.println("4. List users");
            System.out.println("5. View all orders");
            System.out.println("0. Back");
            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> listProducts();
                case 3 -> addUser();
                case 4 -> listUsers();
                case 5 -> listOrders(null);
                case 0 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addProduct() {
        System.out.println("Type: 1 - Book, 2 - Electronics, 3 - Clothing");
        int type = readInt("Choose type: ");
        String name = readString("Name: ");
        double price = readDouble("Price: ");
        int stock = readInt("Stock quantity: ");
        switch (type) {
            case 1 -> {
                String author = readString("Author: ");
                products.add(new Book(nextProductId++, name, price, stock, author));
            }
            case 2 -> {
                int warranty = readInt("Warranty (months): ");
                products.add(new Electronics(nextProductId++, name, price, stock, warranty));
            }
            case 3 -> {
                String size = readString("Size: ");
                products.add(new Clothing(nextProductId++, name, price, stock, size));
            }
            default -> {
                System.out.println("Invalid type. Product not added.");
                return;
            }
        }
        System.out.println("Product added.");
    }

    private static void addUser() {
        System.out.println("Type: 1 - Customer, 2 - Employee");
        int type = readInt("Choose type: ");
        if (type != 1 && type != 2) {
            System.out.println("Invalid type. User not added.");
            return;
        }
        String name = readString("Name: ");
        String email = readString("Email: ");
        String password = readString("Password: ");
        if (type == 1) {
            String address = readString("Address: ");
            users.add(new Customer(name, email, password, address));
        } else {
            String position = readString("Position: ");
            users.add(new Employee(name, email, password, position));
        }
        System.out.println("User added.");
    }


    private static void customerMenu() {
        String email = readString("Enter your email: ");
        Customer customer = findCustomer(email);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.println("Welcome, " + customer.getName() + "!");
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. List products");
            System.out.println("2. Place order");
            System.out.println("3. View my orders");
            System.out.println("0. Back");
            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> listProducts();
                case 2 -> placeOrder(customer);
                case 3 -> listOrders(customer);
                case 0 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void placeOrder(Customer customer) {
        Order order = new Order(nextOrderId, customer);
        while (true) {
            listProducts();
            int id = readInt("Enter product id to add (0 to finish): ");
            if (id == 0) break;
            Product product = findProduct(id);
            if (product == null) {
                System.out.println("Product not found.");
            } else if (order.addProduct(product)) {
                System.out.println("Added: " + product.getName());
            } else {
                System.out.println("Out of stock.");
            }
        }
        if (order.getProducts().isEmpty()) {
            System.out.println("Order cancelled (no products).");
            return;
        }
        orders.add(order);
        nextOrderId++;
        order.printReceipt();
    }


    private static void listProducts() {
        System.out.println("\n--- Products ---");
        if (products.isEmpty()) {
            System.out.println("No products.");
        }
        for (Product p : products) {
            p.displayInfo();
        }
    }

    private static void listUsers() {
        System.out.println("\n--- Users ---");
        for (User u : users) {
            u.displayInfo();
        }
    }


    private static void listOrders(Customer customer) {
        System.out.println("\n--- Orders ---");
        boolean found = false;
        for (Order o : orders) {
            if (customer == null || o.getCustomer() == customer) {
                o.printReceipt();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No orders.");
        }
    }


    private static Customer findCustomer(String email) {
        for (User u : users) {
            if (u instanceof Customer && u.getEmail().equalsIgnoreCase(email)) {
                return (Customer) u;
            }
        }
        return null;
    }

    private static Product findProduct(int id) {
        for (Product p : products) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}