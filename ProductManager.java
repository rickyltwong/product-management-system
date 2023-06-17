package assignment2;

import java.io.*;
import java.util.*;

public class ProductManager {

	private List<Product> products=new ArrayList<>();


	//WiteFile
	public void WiteFile(List<Product> products) throws IOException,ClassNotFoundException {
		try(ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream("products.dat"))){
			for (Product product:products) {
				output.writeObject(product);
			}
		}

	}

	//ReadFile
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

		return ReadProducts;
	}

	public void addProduct(Product prodcuct) throws ClassNotFoundException, IOException {
		products.add(prodcuct);
		WiteFile(products);
	}

	//PrintAll
	public void PrintList(List<Product> products) {

		for (Product product:products) {
			System.out.println(product);
		}

	}

	// DisplayByRange
	public List<Product> DisplayByRange(double priceFrom,double priceTo) throws ClassNotFoundException, IOException {
		List<Product> priceRangeList=ReadFile();

		for (int i=0;i<priceRangeList.size();i++) {
			Product product=priceRangeList.get(i);
			if ((product.getUnitPrice()< priceFrom) || (product.getUnitPrice()> priceTo)){
				priceRangeList.remove(i);
				i--;
			}
		}

		return priceRangeList;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
