package assignment2;

import java.io.*;
import java.util.*;

public class ProductManager {
	
	private List<Product> products=new ArrayList<>();
	
	public void WiteFile(List<Product> products) throws IOException,ClassNotFoundException {
		
		try(ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream("products.dat"))){
			for (Product product:products) {
				output.writeObject(product);
			}
		}
		
	}
	
	public List<Product> ReadFile() throws IOException, ClassNotFoundException {
		
		List<Product> ReadProducts=new ArrayList<>();
		try(ObjectInputStream input= new ObjectInputStream(new FileInputStream("products.dat"))){
			while(true) {
				try {
				ReadProducts.add((Product)(input.readObject()));}
			catch(EOFException ex) {
				break;}
			}
		}
		
		for (Product product : ReadProducts) {
            System.out.println(product);
        }
		return ReadProducts;
	}
	
	public void addProduct(Product prodcuct) throws ClassNotFoundException, IOException {
		products.add(prodcuct);
		WiteFile(products);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
