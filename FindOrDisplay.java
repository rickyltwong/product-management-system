package assignment2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class FindOrDisplay extends JFrame{
	
	private static String TITLE = "Find/Display Products";
	
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
	
	public FindOrDisplay() {
		
		setTitle(TITLE);
		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		CreatPriceRange();
		CreateKeyword();
		CreateAll();
		CreateTextArea();
		
		ButtonGroup group=new ButtonGroup();
		group.add(AllButton);
		group.add(KeywordButton);
		group.add(PriceRangeButton);
		
		add(PriceRangePanel);
		add(KeywordPanel);
		add(AllPanel);
		add(TextDisplayPanel);
	}
	
	
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
		
		ActionListener a1=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				double priceFrom=Double.parseDouble(PriceRangeFrom.getText());
				double priceTo=Double.parseDouble(PriceRangeTo.getText());
				List<Product> products=new ArrayList<>();
				try {
					products=DisplayByRange(priceFrom,priceTo);
					for (Product product:products)
						TextDisplay.append(product.toString()+"\n");
					
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		};
		
		FindDisplay.addActionListener(a1);
	}
	
	public void CreateKeyword() {
		KeywordPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		KeywordButton=new JRadioButton("Keyword");
		KeywordText=new JTextField(8);
		
		KeywordPanel.add(KeywordButton);
		KeywordPanel.add(KeywordText);
		
		ActionListener a1=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name= KeywordText.getText();
				try {
					Product product=DisplayByName(name);
					if (product==null)
						TextDisplay.setText("No item in the list.");
					else
						TextDisplay.append(product.toString()+"\n");
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		};
		
		KeywordText.addActionListener(a1);
	}
	
	public void CreateAll() {
		AllPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		AllButton=new JRadioButton("All");
		AllPanel.add(AllButton);
		
		ActionListener a1=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<Product> products=ProductFileHandler.getProductListFromFile();
					for (Product product:products)
						TextDisplay.append(product.toString()+"\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		};
		AllButton.addActionListener(a1);
	}
	
	public void CreateTextArea() {
		TextDisplayPanel = new JPanel(new BorderLayout());
	    TextDisplay = new JTextArea();
	    TextDisplay.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    TextDisplayPanel.add(TextDisplay, BorderLayout.CENTER);
		
	}
	// DisplayByRange
		public List<Product> DisplayByRange(double priceFrom,double priceTo) throws ClassNotFoundException, IOException {
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
		public Product DisplayByName(String name) throws IOException,ClassNotFoundException {
			List<Product> products=ProductFileHandler.getProductListFromFile();
			
			for (int i=0;i<products.size();i++) {
				Product product=products.get(i);
				if (product.getName().equals(name)) {
					return product;
				}
			}
			return null;
		}

}
