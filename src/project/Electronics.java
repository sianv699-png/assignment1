package project;

public class Electronics extends Product {
    private int warrantyMonths;

    public Electronics(int id, String name, double price, int stockQuantity, int warrantyMonths) {
        super(id, name, price, stockQuantity);
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }

    @Override
    public void displayInfo() {
        System.out.println("[Electronics] [" + getId() + "] " + getName() + " | warranty: " + warrantyMonths
                + " months | price: " + getPrice() + " | stock: " + getStockQuantity());
    }
}