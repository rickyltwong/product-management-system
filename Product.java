package assignment2_ProductManagementSystem;

import java.io.Serializable;

/*
 * Student Name: Ricky Wong, Xianbin Bian
 * Student ID: N01581738, N01553051
 * Section: IGA
 * Logic: This class is used to store product information and perform validation.
 */

public class Product {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private double unitPrice;

    public Product(){}; // Default constructor

    public Product(int id, String name, String description, int quantity, double unitPrice) {
        setId(id);
        setName(name);
        setDescription(description);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        // ID cannot be negative
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        } else this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // Name cannot be empty
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        } else this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        // Quantity cannot be negative
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        } else {
            this.quantity = quantity;
        }
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        // UnitPrice cannot be negative
        if (unitPrice < 0) {
            throw new IllegalArgumentException("UnitPrice cannot be negative");
        } else {
            this.unitPrice = unitPrice;
        }
    }

    @Override
    public String toString() {
        return "Product [ID=" + id + ", name=" + name + ", description=" + description + ", quantity=" + quantity
                + ", unitPrice=" + unitPrice + "]";
    }

}
