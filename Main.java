package assignment2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
 * Student Name: Ricky Wong
 * Student ID: N01581738
 * Section: IGA
 * Logic: This class is responsible for creating the main GUI including the menu,
 * attaching event listeners to the menu items.
 */

public class Main extends JFrame {
	private static final String STR_MANIPULATE = "Add/Update";
	private static final String STR_QUERY = "Find/Display";

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
		new Main();
	}

	public Main() {
		createUI();
		setFrameConfig();
		attachEventListeners();
	}

	private void createUI() {
		// Create the frame
		String title = "Product Main GUI";
		frame = new JFrame(title);

		// Create the menu bar, menu, and menu items
		mb = new JMenuBar();
		menuFile = new JMenu("File");
		itemExit = new JMenuItem("Exit");
		menuFile.add(itemExit);
		menuProduct = new JMenu("Product");
		itemAddOrUpdate = new JMenuItem(STR_MANIPULATE);
		itemFindOrDisplay = new JMenuItem(STR_QUERY);
		menuProduct.add(itemAddOrUpdate);
		menuProduct.add(itemFindOrDisplay);

		// Create the main text
		mainText = new JLabel("Product Management System");
		mainText.setFont(new Font("Courier", Font.BOLD, 24));
		mainText.setHorizontalAlignment(JLabel.CENTER);

		// Add the components to the frame
		mb.add(menuFile);
		mb.add(menuProduct);
		frame.setJMenuBar(mb);
		frame.add(mainText, BorderLayout.CENTER);

	}

	private void setFrameConfig() {
		// Configure the frame
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		int WINDOW_WIDTH = 500;
		int WINDOW_HEIGHT = 400;
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}

	private void attachEventListeners() {

		ActionListener event = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Perform the action based on the menu item selected
				JMenuItem source = (JMenuItem) e.getSource();
				String text = source.getText();

				// Exit the program
				if ("Exit".equals(text)) System.exit(0);

				// Open the other Window
				else if (STR_MANIPULATE.equals(text)) {
					frame.dispose();
					new AddOrUpdate();
				} else if (STR_QUERY.equals(text)) {
					frame.dispose();
					new FindOrDisplay();
				}

			}

		};

		// Attach the event listeners
		itemExit.addActionListener(event);
		itemAddOrUpdate.addActionListener(event);
		itemFindOrDisplay.addActionListener(event);

	}

}
