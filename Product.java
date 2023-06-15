package assignment2_ProductManagementSystem;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;

/*
 * Student Name: Ricky Wong
 * Student ID: N01581738
 * Section: IGA
 * Logic: This class is used to store product information.
 */

public class Product implements Serializable {
    private int productID;
    private String name;
    private String description;
    private int quantity;
    private double unitPrice;

    public Product(int productID, String name, String description, int quantity, double unitPrice) {

        // Get the next product ID
//        try {
//
//            // TODO - build a logic to read the lastProduct id in the file and increment it by 1
//
//            // TODO - write the product to the file
//
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        setProductID(productID);
        setName(name);
        setDescription(description);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toUpperCase();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
        if (unitPrice < 0) {
            throw new IllegalArgumentException("UnitPrice cannot be negative");
        } else {
            this.unitPrice = unitPrice;
        }
    }

    @Override
    public String toString() {
        return String.format("%-10d %-20s %-20s %-10d %-10.2f", productID, name, description, quantity, unitPrice);
    }

}
