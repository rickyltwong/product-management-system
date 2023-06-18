package assignment2;

import assignment2.Product;
import assignment2.ProductFileHandler;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Student Name: Ricky Wong, Emma Zhang
 * Student ID: N01581738, N01587845
 * Section: IGA
 * Logic: This class is the GUI class with functionalities like adding and updating a product.
 */

public class AddOrUpdate extends JFrame {
    private static final String TITLE = "Add/Update Product";
    private Map<String, JButton> buttonsMap;
    private Map<String, JLabel> labelsMap;
    private Map<String, JTextField> textFieldsMap;
    private JTextArea txtDescription;

    private static final int NAME_MAX_LENGTH = 20;
    private static final int DESCRIPTION_MAX_LENGTH = 100;
    private static int currentProduct = 0;
    Product product;

    public AddOrUpdate() {
        createUI();
        setFrameConfig();
        attachEventListeners();
    }

    private void createUI() {
        this.setTitle(TITLE);
        this.setLayout(null);

        // Create labels and text fields
        JLabel lblProductId = new JLabel("Product ID");
        JLabel lblName = new JLabel("Name");
        JLabel lblDescription = new JLabel("Description");
        JLabel lblQuantity = new JLabel("Quantity in hand");
        JLabel lblPrice = new JLabel("Unit Price");
        JTextField txtProductId = new JTextField();
        JTextField txtName = new JTextField(100);
        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        JTextField txtQuantity = new JTextField();
        JTextField txtPrice = new JTextField();

        JLabel prompt = new JLabel();

        labelsMap = new HashMap<>();
        labelsMap.put("Product ID", lblProductId);
        labelsMap.put("Name", lblName);
        labelsMap.put("Description", lblDescription);
        labelsMap.put("Quantity in hand", lblQuantity);
        labelsMap.put("Unit Price", lblPrice);
        labelsMap.put("prompt", prompt);

        textFieldsMap = new HashMap<>();
        textFieldsMap.put("Product ID", txtProductId);
        textFieldsMap.put("Name", txtName);
        textFieldsMap.put("Quantity in hand", txtQuantity);
        textFieldsMap.put("Unit Price", txtPrice);

        // Create buttons
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnFirst = new JButton("First");
        JButton btnPrevious = new JButton("Previous");
        JButton btnNext = new JButton("Next");
        JButton btnLast = new JButton("Last");
        buttonsMap = new HashMap<>();
        buttonsMap.put("Add", btnAdd);
        buttonsMap.put("Update", btnUpdate);
        buttonsMap.put("First", btnFirst);
        buttonsMap.put("Previous", btnPrevious);
        buttonsMap.put("Next", btnNext);
        buttonsMap.put("Last", btnLast);

        // Set bounds
        lblProductId.setBounds(30, 20, 100, 20);
        lblName.setBounds(30, 60, 100, 20);
        lblDescription.setBounds(30, 100, 100, 20);
        lblQuantity.setBounds(330, 100, 120, 20);
        lblPrice.setBounds(330, 140, 100, 20);
        prompt.setBounds(300, 200, 150, 20);
        prompt.setForeground(Color.RED);

        txtProductId.setBounds(160, 20, 120, 20);
        txtName.setBounds(160, 60, 200, 20);
        txtDescription.setBounds(160, 100, 140, 65);
        txtQuantity.setBounds(480, 100, 100, 20);
        txtPrice.setBounds(480, 140, 100, 20);
        btnAdd.setBounds(30, 200, 120, 20);
        btnUpdate.setBounds(170, 200, 120, 20);
        btnFirst.setBounds(30, 230, 120, 20);
        btnPrevious.setBounds(170, 230, 120, 20);
        btnNext.setBounds(320, 230, 120, 20);
        btnLast.setBounds(470, 230, 120, 20);

        // Add to frame
        for (JLabel label : labelsMap.values()) {
            this.add(label);
        }

        for (JTextField textField : textFieldsMap.values()) {
            this.add(textField);
        }

        this.add(txtDescription);

        for (JButton button : buttonsMap.values()) {
            this.add(button);
        }
    }

    private void setFrameConfig() {

        // Configure the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int WINDOW_WIDTH = 650;
        int WINDOW_HEIGHT = 350;
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
    }

    private void attachEventListeners() {

        // Add key listener to limit the length of name to 20 characters
        JTextField tfName = textFieldsMap.get("Name");
        tfName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (tfName.getText().length() >= NAME_MAX_LENGTH) // limit to 20 characters
                    e.consume();
            }
        });

        // Add key listener to limit the length of description to 100 characters
        txtDescription.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (txtDescription.getText().length() >= DESCRIPTION_MAX_LENGTH) // limit to 100 characters
                    e.consume();
            }
        });


        /*
         * Add, First, Previous, Next, Last, Update Buttons action listeners
         */

        buttonsMap.get("Add").addActionListener(evt -> {

            String name = textFieldsMap.get("Name").getText();
            String description = txtDescription.getText();
            int productId = Integer.parseInt(textFieldsMap.get("Product ID").getText());
            int quantity = Integer.parseInt(textFieldsMap.get("Quantity in hand").getText());
            double price = Double.parseDouble(textFieldsMap.get("Unit Price").getText());

            // Check if there is duplicate id (in-place search)
            int pos = -1;
            try {
                pos = ProductFileHandler.getProductBytePosition(productId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (pos != -1) {
                JOptionPane.showMessageDialog(this, "Product ID already exists");
                return;
            }

            // Write the product into file
            Product product = new Product(productId, name, description, quantity, price);
            try {
                ProductFileHandler.addProduct(product);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Print out All products (for testing)
            System.out.println("Record Added!");
            labelsMap.get("prompt").setText("Record Added!");
            try {
                ProductFileHandler.printAllProducts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        buttonsMap.get("Update").addActionListener(evt -> {

            int productId = Integer.parseInt(textFieldsMap.get("Product ID").getText());
            String name = textFieldsMap.get("Name").getText();
            String description = txtDescription.getText();
            int quantity = Integer.parseInt(textFieldsMap.get("Quantity in hand").getText());
            double price = Double.parseDouble(textFieldsMap.get("Unit Price").getText());

            try {
                ArrayList<Product> products = ProductFileHandler.getProductListFromFile();
                Product productToUpdate = findProductInList(products, productId);

                if (productToUpdate == null) {
                    JOptionPane.showMessageDialog(this, "Product ID not found");
                    return;
                }

                // Update the product
                productToUpdate.setName(name);
                productToUpdate.setDescription(description);
                productToUpdate.setQuantity(quantity);
                productToUpdate.setUnitPrice(price);

                DataOutputStream dos = ProductFileHandler.getDataOutputStream();
                for (Product product : products) {
                    ProductFileHandler.addProduct(product);
                }

                // Close the data output stream
                dos.close();

                // Print out All products (for testing)
                System.out.println("Record Updated!");
                labelsMap.get("prompt").setText("Record Updated!");
                ProductFileHandler.printAllProducts();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonsMap.get("First").addActionListener(evt -> {
            labelsMap.get("prompt").setText("");
            try {
                firstProduct();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
            displayRecord();

        });

        buttonsMap.get("Previous").addActionListener(evt -> {
            labelsMap.get("prompt").setText("");
            try {
                previousProduct();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            displayRecord();
        });


        buttonsMap.get("Next").addActionListener(evt -> {
            labelsMap.get("prompt").setText("");
            try {
                nextProduct();
                if (product != null) {
                    displayRecord();

                }
            } catch (Exception e1) {
                e1.printStackTrace();
                labelsMap.get("prompt").setText("An error occurred: " + e1.getMessage());
            }

        });

        buttonsMap.get("Last").addActionListener(evt -> {
            labelsMap.get("prompt").setText("");
            try {
                lastProduct();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            displayRecord();
        });

    }

    public void displayRecord() {
        textFieldsMap.get("Product ID").setText(String.valueOf(product.getId()));
        textFieldsMap.get("Name").setText(product.getName().trim());
        txtDescription.setText(product.getDescription().trim());
        textFieldsMap.get("Quantity in hand").setText(String.valueOf(product.getQuantity()));
        textFieldsMap.get("Unit Price").setText(String.valueOf(product.getUnitPrice()));
    }

    public void firstProduct() throws IOException {
        currentProduct = 0;
        product = ProductFileHandler.readProduct(currentProduct);
    }

    public void previousProduct() throws IOException {
        if (currentProduct > 0) {
            currentProduct--;
            product = ProductFileHandler.readProduct(currentProduct);

        } else {
            product = ProductFileHandler.readProduct(currentProduct);
            labelsMap.get("prompt").setText("This is the first record");
        }

    }

    public void nextProduct() throws Exception {
        if (currentProduct < ProductFileHandler.getProductsCount() - 1) {
            currentProduct++;
            product = ProductFileHandler.readProduct(currentProduct);
            if (product == null || product.getName().isEmpty()) {
                throw new Exception("Product not found");
            }
        } else {
            labelsMap.get("prompt").setText("This the last record");
            product = ProductFileHandler.readProduct(currentProduct);
            if (product == null || product.getName().isEmpty()) {
                throw new Exception("Product not found");
            }
        }

    }

    public void lastProduct() throws IOException {
        currentProduct = ProductFileHandler.getProductsCount() - 1;
        product = ProductFileHandler.readProduct(currentProduct);

    }

    public static Product findProductInList(ArrayList<Product> products, int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    // Main method for testing
    public static void main(String[] args) throws IOException {
        AddOrUpdate addOrUpdate = new AddOrUpdate();
    }
}
