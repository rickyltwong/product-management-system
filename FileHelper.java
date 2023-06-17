package ProductManagementSystem;

import java.io.*;
import java.util.ArrayList;

/*
 * Student Name: Emma Zhang, Xiaoying Bian, Ricky Wong
 * Student ID: N01587845, 01553051, N01581738
 * Section: IGA
 * Logic: This class is the helper class for file operations.
 */
public final class FileHelper {

    private FileHelper() {}

    // File path: Please change the path to your own path
    private static final String filePath = "src/ProductManagementSystem/products.dat";

    private static final File file = new File(filePath);
    private int currentProduct;
  
    private static final int RECORD_SIZE = 140;
    
    public static DataOutputStream getDataOutputStreamWithAppend() throws IOException {
        return new DataOutputStream(new FileOutputStream(file, true));
    }

    public static DataOutputStream getDataOutputStream() throws IOException {
        return new DataOutputStream(new FileOutputStream(file , true));
    }

    public static DataInputStream getDataInputStream() throws IOException {
        return new DataInputStream(new FileInputStream(file));
    }

    // Write the product into file
    public static void addProduct(Product product) throws IOException {
        try (DataOutputStream dos = getDataOutputStreamWithAppend()) {
            writeProductToStream(dos, product);
        }
    }
    // Write the product into file
    private static void writeProductToStream(DataOutputStream dos, Product product) throws IOException {
        dos.writeInt(product.getId());
        String paddedName = String.format("%1$-" + 20 + "s", product.getName());
        String paddedDescription = String.format("%1$-" + 100 + "s", product.getDescription());
        dos.writeUTF(paddedName);
        dos.writeUTF(paddedDescription);
        dos.writeInt(product.getQuantity());
        dos.writeDouble(product.getUnitPrice());
    }

    public static Product readProduct(int currentProduct) throws IOException  {
    	try(DataInputStream input = new DataInputStream(new FileInputStream(filePath))) {
    		//skip over previous records
    		input.skip(currentProduct * RECORD_SIZE);
    		
    		//read current product record
    		int productID = input.readInt();
    		String name = input.readUTF();
    		String description = input.readUTF();
    		int qty = input.readInt();
    		double price = input.readDouble();
    		
    		//create a new instance
    		return new Product(productID, name, description, qty,  price);
    		
    	}
    }
  //method to read one record in the file and return a product object

    public static int getProductsCount() {
    	int productsCount;
    	
    	File file = new File(filePath);
    	productsCount =(int)(file.length() / RECORD_SIZE);
    	return productsCount;
    }
    
    public static int getProductBytePosition(int targetProductId) throws IOException {
        DataInputStream dis = getDataInputStream();
        int position = 0;
        while (dis.available() > 0) {
            int currentProductId = dis.readInt();
            position += 4; // 4 bytes for integer id
            if (currentProductId == targetProductId) {
                return position - 4; // Subtract 4 because we want the position at the start of the product
            } else {
                skipProduct(dis);
                position += 136; // Total size of a product record, minus 4 because we've already read the ID
            }
        }
        dis.close();

        return -1; // Return -1 if the product is not found
    }

    // Read the file, store it into an arraylist and return it
    public static ArrayList<Product> getProductListFromFile() throws IOException {

        ArrayList<Product> productList = new ArrayList<>();
        DataInputStream dis = getDataInputStream();

        while (dis.available() > 0) {
            Product product = new Product();
            product.setId(dis.readInt());
            product.setName(dis.readUTF());
            product.setDescription(dis.readUTF());
            product.setQuantity(dis.readInt());
            product.setUnitPrice(dis.readDouble());
            productList.add(product);
        }
        return productList;
    }

    // Check if there is duplicate productID
    public static boolean isDuplicateId(int productId) throws IOException {
        ArrayList<Product> productList = getProductListFromFile();
        for (Product product : productList) {
            if (product.getId() == productId) {
                return true;
            }
        }
        return false;
    }
    
    private static void skipProduct(DataInputStream dis) throws IOException {
        dis.skipBytes(22); // Skip name (2 bytes of length infomration + 20 bytes of name)
        dis.skipBytes(102); // Skip description (2 bytes of length infomration + 100 bytes of description)
        dis.skipBytes(4); // Skip quantity
        dis.skipBytes(8); // Skip unit price
    }

    // Get Product from Product ID
//    public static Product getProductFromFile(int productId) throws IOException {
//    }

    // Print out all products in the file for testing purpose
    public static void printAllProducts() throws IOException {
        ArrayList<Product> productList = getProductListFromFile();
        for (Product product : productList) {
            System.out.println(product);
        }
    }
}
