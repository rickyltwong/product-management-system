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
	ProductManager product_manager=new ProductManager();
	
	public FindOrDisplay() {
		
		setLayout(new GridLayout(4,1));
		setTitle(TITLE);
		CreatPriceRange();
		CreateKeyword();
		
		CreateLayout();
	}
	
	public void CreatPriceRange() {
		PriceRangePanel=new JPanel();
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
					products=product_manager.DisplayByRange(priceFrom,priceTo);
					product_manager.PrintList(products);
					
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		};
		
		FindDisplay.addActionListener(a1);
	}
	
	public void CreateKeyword() {
		KeywordPanel=new JPanel();
		KeywordButton=new JRadioButton("Keyword");
		KeywordText=new JTextField(8);
		
		KeywordPanel.add(KeywordButton);
		KeywordPanel.add(KeywordText);
		
		ActionListener a1=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name= KeywordText.getText();
				ProductManager.DisplayByName(name);
				
			}
		};
		
		KeywordButton.addActionListener(a1);
	}
	
	public void CreateLayout() {
		add(PriceRangePanel);
		add(KeywordPanel);
	}

}
