package assignment2_ProductManagementSystem;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
 * Student Name: Ricky Wong
 * Student ID: N01581738
 * Section: IGA
 * Logic: This class is used to add or update a product.
 */

public class AddOrUpdate {
    private static final String filePath = "src\\assignment2_ProductManagementSystem\\products.bin";
    private static int nextProductId;
    private static int lastProductId = -1;

    JFrame frame;
    Map<String, JButton> buttonsMap;

    public AddOrUpdate() {
        createUI();
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
        JTextArea txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        JTextField txtQuantity = new JTextField();
        JTextField txtPrice = new JTextField();

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
        frame.add(lblProductId);
        frame.add(lblName);
        frame.add(lblDescription);
        frame.add(lblQuantity);
        frame.add(lblPrice);
        frame.add(txtProductId);
        frame.add(txtName);
        frame.add(txtDescription);
        frame.add(txtQuantity);
        frame.add(txtPrice);

        for (JButton button : buttonsMap.values()) {
            frame.add(button);
        }

        /*
         Add action listeners:

         Add - should add the information of new product to the file, product ID should be unique, quantity in hand and unit price should be a number and above 0 and name is required.

         First - Should read the first record stored in the file and display the details of it in the controls

         Previous – if possible, should read the previous record to the current record stored in the file and display the details of it in the controls.

         Next – if possible, should read the next record to the current record stored in the file and display the details of it in the controls.

         Last – Should read the last record stored in the file and display the details of it in the controls.

         Update – should update the information of currently displayed record on the GUI.
        */

        btnAdd.addActionListener(evt -> {

            class AppendableObjectOutputStream extends ObjectOutputStream {
                public AppendableObjectOutputStream(OutputStream out) throws IOException {
                    super(out);
                }

                @Override
                protected void writeStreamHeader() throws IOException {
                }
            }


            try {
                int productId = Integer.parseInt(txtProductId.getText());
                String name = txtName.getText();
                String description = txtDescription.getText();
                int quantity = Integer.parseInt(txtQuantity.getText());
                double unitPrice = Double.parseDouble(txtPrice.getText());

                File file = new File(filePath);
                boolean fileExists = file.exists() && file.length() > 0;
                FileOutputStream fos = new FileOutputStream(file, true);
                ObjectOutputStream oos = fileExists
                        ? new AppendableObjectOutputStream(fos)
                        : new ObjectOutputStream(fos);


                Product product = new Product(productId, name, description, quantity, unitPrice);
                oos.writeObject(product);
                oos.close();
                fos.close();


                if (!file.exists()) {
                    System.out.println("The file is empty or does not exist.");
                    return;
                }

                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    while (true) {
                        try {
                            Product productRead = (Product) ois.readObject();
                            System.out.println(productRead);
                        } catch (EOFException ex) {
                            break;
                        }
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }


                JOptionPane.showMessageDialog(null, "Product added successfully");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        // Configure the frame
        setFrameConfig();

    }

    private void setFrameConfig() {

        // Configure the frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int WINDOW_WIDTH = 650;
        int WINDOW_HEIGHT = 350;
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    // Main method for testing
    public static void main(String[] args) throws FileNotFoundException {
        AddOrUpdate addOrUpdate = new AddOrUpdate();

    }
}

