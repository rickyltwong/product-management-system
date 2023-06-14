package assignment2_ProductManagementSystem;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainProduct extends JFrame {

	private static int WINDOW_HEIGHT = 400;
	private static int WINDOW_WIDTH = 500;
	private static String TITLE = "Main Product GUI";
	private static String STR_MANIPULATE = "Add/Update";
	private static String STR_QUERY = "Find/Display";

	// GUI components
	JFrame frame;
	JMenuBar mb;
	JMenu menuFile;
	JMenuItem itemExit;
	JMenu menuProduct;
	JMenuItem itemAddOrUpdate;
	JMenuItem itemFindOrDisplay;
	JLabel mainText;

	public static void main(String[] args) {
		new MainProduct();
	}

	public MainProduct() {
		createGUI();
		setFrameConfig();
		attachEventListeners();
	}

	private void createGUI() {
		frame = new JFrame(TITLE);
		mb = new JMenuBar();

		menuFile = new JMenu("File");
		itemExit = new JMenuItem("Exit");
		menuFile.add(itemExit);

		menuProduct = new JMenu("Product");
		itemAddOrUpdate = new JMenuItem(STR_MANIPULATE);
		itemFindOrDisplay = new JMenuItem(STR_QUERY);
		menuProduct.add(itemAddOrUpdate);
		menuProduct.add(itemFindOrDisplay);

		mainText = new JLabel("Product Management System");
		mainText.setFont(new Font("Courier", Font.BOLD, 24));
		mainText.setHorizontalAlignment(JLabel.CENTER);

		mb.add(menuFile);
		mb.add(menuProduct);
		frame.setJMenuBar(mb);
		frame.add(mainText, BorderLayout.CENTER);

	}

	private void setFrameConfig() {

		// Configure the frame
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}

	private void attachEventListeners() {

		ActionListener event = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem source = (JMenuItem) e.getSource();
				String text = source.getText();

				if ("Exit".equals(text)) System.exit(0);
				else if (STR_MANIPULATE.equals(text)) {
					frame.dispose();
					new AddOrUpdate();
				} else if (STR_QUERY.equals(text)) {
					frame.dispose();
					new FindOrDisplay();
				}

			}

		};

		itemExit.addActionListener(event);
		itemAddOrUpdate.addActionListener(event);
		itemFindOrDisplay.addActionListener(event);

	}

}
