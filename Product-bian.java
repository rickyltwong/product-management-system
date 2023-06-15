package assignment2;

import java.io.Serializable;

public class Product implements Serializable{
	private static int initialID=0;
	private int ID;
	private String name;
	private String description;
	private int quantity;
	private double unitPrice;
	
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
