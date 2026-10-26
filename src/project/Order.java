package project;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private Customer customer;
    private ArrayList<Product> products;
    private double totalAmount;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = new ArrayList<>();
        this.totalAmount = 0;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public ArrayList<Product> getProducts() { return products; }
    public double getTotalAmount() { return totalAmount; }


    public boolean addProduct(Product product) {
        if (product.getStockQuantity() <= 0) {
            return false;
        }
        product.setStockQuantity(product.getStockQuantity() - 1);
        products.add(product);
        totalAmount += product.getPrice();
        return true;
    }

    public void printReceipt() {
        System.out.println("\n----- RECEIPT -----");
        System.out.println("Order #" + orderId);
        System.out.println("Customer: " + customer.getName() + " (" + customer.getEmail() + ")");
        for (Product p : products) {
            System.out.println(" - " + p.getName() + ": " + p.getPrice());
        }
        System.out.println("TOTAL: " + totalAmount);
        System.out.println("-------------------");
    }
}