package project;

public class Customer extends User {
    private String address;

    public Customer(String name, String email, String password, String address) {
        super(name, email, password, "customer");
        this.address = address;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public void displayInfo() {
        System.out.println("Customer: " + getName() + " (" + getEmail() + "), address: " + address);
    }
}