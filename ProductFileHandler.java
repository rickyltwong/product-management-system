package assignment2_ProductManagementSystem;

import java.io.*;
import java.util.ArrayList;

/*
 * Student Name: Emma Zhang, Xiaoying Bian, Ricky Wong
 * Student ID: N01587845, 01553051, N01581738
 * Section: IGA
 * Logic: This class is the helper class for file operations.
 */
public final class ProductFileHandler {

    private ProductFileHandler() {
    }

    // File path: Please change the path to your own path
    private static final String filePath = "src\\assignment2_ProductManagementSystem\\products.dat";

    private static final File file = new File(filePath);

    public static DataOutputStream getDataOutputStreamWithAppend() throws IOException {
        return new DataOutputStream(new FileOutputStream(file, true));
    }

    public static DataOutputStream getDataOutputStream() throws IOException {
        return new DataOutputStream(new FileOutputStream(file));
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


    // Read the whole file, store products into an arraylist and return it
    public static ArrayList<Product> getProductListFromFile() throws IOException {
        ArrayList<Product> productList = new ArrayList<>();
        try (DataInputStream dis = getDataInputStream()) {
            while (dis.available() > 0) {
                productList.add(readProductFromStream(dis));
            }
        }
        return productList;
    }

    // Read a product from the file
    private static Product readProductFromStream(DataInputStream dis) throws IOException {
        Product product = new Product();
        product.setId(dis.readInt());
        product.setName(dis.readUTF().trim());
        product.setDescription(dis.readUTF().trim());
        product.setQuantity(dis.readInt());
        product.setUnitPrice(dis.readDouble());
        return product;
    }

    // Check if there is duplicate Product ID in the file (checking productID in an arraylist)
    public static boolean hasDuplicateId(int productId) throws IOException {
        ArrayList<Product> productList = getProductListFromFile();
        for (Product product : productList) {
            if (product.getId() == productId) {
                return true;
            }
        }
        return false;
    }

    // Get the position of the product using Product ID (checking productID in the file)
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

    private static void skipProduct(DataInputStream dis) throws IOException {
        dis.skipBytes(22); // Skip name (2 bytes of length infomration + 20 bytes of name)
        dis.skipBytes(102); // Skip description (2 bytes of length infomration + 100 bytes of description)
        dis.skipBytes(4); // Skip quantity
        dis.skipBytes(8); // Skip unit price
    }

    // Print out all products in the file for testing purpose
    public static void printAllProducts() throws IOException {
        ArrayList<Product> productList = getProductListFromFile();
        for (Product product : productList) {
            System.out.println(product);
        }
    }
}
