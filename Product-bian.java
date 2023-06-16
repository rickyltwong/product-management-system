package assignment2;

import java.io.Serializable;

public class Product implements Serializable{
	private static int initialID=0;
	private int ID;
	private String name;
	private String description;
	private int quantity;
	private double unitPrice;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Product(String name,String description,int quantity,double unitPrice) {
		this.ID=initialID++;
		this.name=name;
		this.description=description;
		this.quantity=quantity;
		this.unitPrice=unitPrice;
		
	}

	@Override
	public String toString() {
		return "Product [ID=" + ID + ", name=" + name + ", description=" + description + ", quantity=" + quantity
				+ ", unitPrice=" + unitPrice + "]";
	}
	
}
