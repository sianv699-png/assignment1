package project;

public class Clothing extends Product {
    private String size;

    public Clothing(int id, String name, double price, int stockQuantity, String size) {
        super(id, name, price, stockQuantity);
        this.size = size;
    }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    @Override
    public void displayInfo() {
        System.out.println("[Clothing] [" + getId() + "] " + getName() + " | size: " + size
                + " | price: " + getPrice() + " | stock: " + getStockQuantity());
    }
}