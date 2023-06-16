package assignment2_ProductManagementSystem;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
 * Student Name: Ricky Wong
 * Student ID: N01581738
 * Section: IGA
 * Logic: This class is the GUI class with functionalities like adding and updating a product.
 */

public class AddOrUpdate {
    JFrame frame;
    Map<String, JButton> buttonsMap;
    Map<String, JLabel> labelsMap;
    Map<String, JTextField> textFieldsMap;
    JTextArea txtDescription;

    private static final int NAME_MAX_LENGTH = 20;
    private static final int DESCRIPTION_MAX_LENGTH = 100;

    public AddOrUpdate() {
        createUI();
        setFrameConfig();
        attachEventListeners();
    }

    private void createUI() {
        frame = new JFrame("Add/Update Product");
        frame.setLayout(null);

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

        labelsMap = new HashMap<>();
        labelsMap.put("Product ID", lblProductId);
        labelsMap.put("Name", lblName);
        labelsMap.put("Description", lblDescription);
        labelsMap.put("Quantity in hand", lblQuantity);
        labelsMap.put("Unit Price", lblPrice);

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
        lblQuantity.setBounds(330, 100, 100, 20);
        lblPrice.setBounds(330, 140, 100, 20);

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
            frame.add(label);
        }

        for (JTextField textField : textFieldsMap.values()) {
            frame.add(textField);
        }

        frame.add(txtDescription);

        for (JButton button : buttonsMap.values()) {
            frame.add(button);
        }
    }

    private void setFrameConfig() {

        // Configure the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int WINDOW_WIDTH = 650;
        int WINDOW_HEIGHT = 350;
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    private void attachEventListeners() {

        // Add key listener to limit the length of name to 10 characters
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
         Other action listeners:

         Add - should add the information of new product to the file, product ID should be unique, quantity in hand and unit price should be a number and above 0 and name is required.

         First - Should read the first record stored in the file and display the details of it in the controls

         Previous – if possible, should read the previous record to the current record stored in the file and display the details of it in the controls.

         Next – if possible, should read the next record to the current record stored in the file and display the details of it in the controls.

         Last – Should read the last record stored in the file and display the details of it in the controls.

         Update – should update the information of currently displayed record on the GUI.
        */

        buttonsMap.get("Add").addActionListener(evt -> {
            String name = textFieldsMap.get("Product ID").getText();
            String description = txtDescription.getText();
            // Pad the name and description with spaces to make them 20 and 100 characters long respectively
            if (name.length() < NAME_MAX_LENGTH) {
                name = String.format("%-" + NAME_MAX_LENGTH + "s", name);
            }
            if (description.length() < DESCRIPTION_MAX_LENGTH) {
                description = String.format("%-" + DESCRIPTION_MAX_LENGTH + "s", description);
            }

            int productId = Integer.parseInt(textFieldsMap.get("Product ID").getText());
            int quantity = Integer.parseInt(textFieldsMap.get("Quantity in hand").getText());
            double price = Double.parseDouble(textFieldsMap.get("Unit Price").getText());

            // Check if there is duplicate id
            boolean isDuplicate = false;
            try {
                isDuplicate = FileHelper.isDuplicateId(productId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (isDuplicate) {
                JOptionPane.showMessageDialog(frame, "Product ID already exists");
                return;
            }

            // Write the product into file
            Product product = new Product(productId, name, description, quantity, price);
            try {
                FileHelper.addProduct(product);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Print out All products (for testing)
            try {
                FileHelper.printAllProducts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        buttonsMap.get("Update").addActionListener(evt -> {

        });

        buttonsMap.get("First").addActionListener(evt -> {

        });

        buttonsMap.get("Previous").addActionListener(evt -> {

        });

        buttonsMap.get("Next").addActionListener(evt -> {

        });

        buttonsMap.get("Last").addActionListener(evt -> {

        });


    }

    // Main method for testing
    public static void main(String[] args) throws FileNotFoundException {
        AddOrUpdate addOrUpdate = new AddOrUpdate();

    }
}

