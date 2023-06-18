package assignment2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class FindOrDisplay {
	
	private static int WINDOW_HEIGHT = 400;
	private static int WINDOW_WIDTH = 500;
	JFrame frame;
	private JPanel PriceRangePanel;
	private JPanel KeywordPanel;
	private JPanel AllPanel;
	private JPanel TextDisplayPanel;
	
	private JRadioButton PriceRangeButton;
	private JRadioButton KeywordButton;
	private JRadioButton AllButton;
	
	private JButton FindDisplay;
	
	private JTextField PriceRangeFrom;
	private JTextField PriceRangeTo;
	private JTextField KeywordText;
	private JTextArea TextDisplay;
	private ActionListener ClearListener; 
	
	public FindOrDisplay() {
		
		frame=new JFrame("Find/Display Products");
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		frame.setContentPane(panel);
		setFrameConfig();
		CreatPriceRange();
		CreateKeyword();
		CreateAll();
		CreateTextArea();
		
		ButtonGroup group=new ButtonGroup();
		group.add(AllButton);
		group.add(KeywordButton);
		group.add(PriceRangeButton);
		
		frame.add(PriceRangePanel);
		frame.add(KeywordPanel);
		frame.add(AllPanel);
		frame.add(TextDisplayPanel);
		
		//clears all text fields and the display area whenever a radio button selection is made
		class clearListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				TextDisplay.setText("");
				PriceRangeFrom.setText("");
				PriceRangeTo.setText("");
				KeywordText.setText("");
			}
			
		}
		ClearListener=new clearListener();
		PriceRangeButton.addActionListener(ClearListener);
		KeywordButton.addActionListener(ClearListener);
		AllButton.addActionListener(ClearListener);
		
	}
	
	public void setFrameConfig() {

		// Configure the frame
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}
	
	//Create PriceRange panel
	public void CreatPriceRange() {
		PriceRangePanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		PriceRangeButton=new JRadioButton("Price Range");
	
		PriceRangeFrom=new JTextField(8);
		PriceRangeTo=new JTextField(8);
		FindDisplay=new JButton("Find/Display");
		
		PriceRangePanel.add(PriceRangeButton);
		PriceRangePanel.add(PriceRangeFrom);
		PriceRangePanel.add(PriceRangeTo);
		PriceRangePanel.add(FindDisplay);
		
		//ActionListener for find/display button
		ActionListener a1=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// display by price range
				if (PriceRangeButton.isSelected()) {
					// make sure the from-to textfield not empty
					if (PriceRangeFrom.getText().isEmpty() || PriceRangeTo.getText().isEmpty()) {
						JOptionPane.showMessageDialog(frame, "Please enter price range.");
						return;
					}
					double priceFrom=Double.parseDouble(PriceRangeFrom.getText());
					double priceTo=Double.parseDouble(PriceRangeTo.getText());
					List<Product> products=new ArrayList<>();
					try {
						//call DisplayByRange method
						TextDisplay.setText("");
						products=DisplayByRange(priceFrom,priceTo);
						for (Product product:products)
							TextDisplay.append(product.toString()+"\n");
					
					} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//display by keyword
				}else  if (KeywordButton.isSelected()) {
					if (KeywordText.getText().isEmpty()){
						JOptionPane.showMessageDialog(frame, "Please enter a product name.");
						return;
					}
					String name= KeywordText.getText();
					try {
						TextDisplay.setText("");
						//call DisplayByName method
						List<Product> products=DisplayByName(name);
						if (products==null)
							TextDisplay.setText("No item in the list.");
						else
							for (Product product:products)
								TextDisplay.append(product.toString()+"\n");
						
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// display all the items in the file
				}else if (AllButton.isSelected()) {
					try {
						TextDisplay.setText("");
						List<Product> products=ProductFileHandler.getProductListFromFile();
						for (Product product:products)
							TextDisplay.append(product.toString()+"\n");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
			
		};
		
		FindDisplay.addActionListener(a1);
	}
	//create keyword panel
	public void CreateKeyword() {
		KeywordPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		KeywordButton=new JRadioButton("Keyword");
		
		KeywordText=new JTextField(8);
		
		KeywordPanel.add(KeywordButton);
		KeywordPanel.add(KeywordText);
		
	}
	//create all panel
	public void CreateAll() {
		AllPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		AllButton=new JRadioButton("All");
		
		AllPanel.add(AllButton);
		
	}
	//create text area
	public void CreateTextArea() {
		TextDisplayPanel = new JPanel(new BorderLayout());
	    TextDisplay = new JTextArea();
	    TextDisplay.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    JScrollPane scrollPane=new JScrollPane(TextDisplay);
	    TextDisplayPanel.add(scrollPane, BorderLayout.CENTER);
		
	}
	// DisplayByRange
		public List<Product> DisplayByRange(double priceFrom,double priceTo) throws ClassNotFoundException, IOException {
			//read all the products from file, filters out those which do not fall within the provided price range.
			List<Product> priceRangeList=ProductFileHandler.getProductListFromFile();

			for (int i=0;i<priceRangeList.size();i++) {
				Product product=priceRangeList.get(i);
				if ((product.getUnitPrice()< priceFrom) || (product.getUnitPrice()> priceTo)){
					priceRangeList.remove(i);
					i--;
				}
			}
			return priceRangeList;
			
		}
		
		// DisplayByName
		public List<Product> DisplayByName(String name) throws IOException,ClassNotFoundException {
			List<Product> products=ProductFileHandler.getProductListFromFile();
			List<Product> result=new ArrayList<Product>();
			for (int i=0;i<products.size();i++) {
				Product product=products.get(i);
				if (product.getName().equals(name)) {
					result.add(product);
				}
			}
			if (result.isEmpty())
				return null;
			else
				return result;
		}

}
