package assignment2_ProductManagementSystem;

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
    private static final String filePath = "src\\assignment2_ProductManagementSystem\\products.dat";

    private static final File file = new File(filePath);

    public static DataOutputStream getDataOutputStream() throws IOException {
        return new DataOutputStream(new FileOutputStream(file , true));
    }

    public static DataInputStream getDataInputStream() throws IOException {
        return new DataInputStream(new FileInputStream(file));
    }

    // Write the product into file
    public static void addProduct(Product product) throws IOException {

        DataOutputStream dos = getDataOutputStream();
        dos.writeInt(product.getId()); // 4 bytes
        dos.writeUTF(product.getName()); // 50 bytes
        dos.writeUTF(product.getDescription()); // 100 bytes
        dos.writeInt(product.getQuantity()); // 4 bytes
        dos.writeDouble(product.getUnitPrice()); // 8 bytes
        dos.close();

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

    // Print out all products in the file for testing purpose
    public static void printAllProducts() throws IOException {
        ArrayList<Product> productList = getProductListFromFile();
        for (Product product : productList) {
            System.out.println(product);
        }
    }
}
