package io.kaeljohnston.sales;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
	static final int WINDOW_HEIGHT = 500;
	static final int WINDOW_WIDTH = 700;

	public static ArrayList<Product> products = new ArrayList<>(); // list of products from start menu
	public static ArrayList<SalesItem> itemList = new ArrayList<>(); // list of product winodws

	static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	public static final Window wndw = new Window(WINDOW_SIZE);

	public static void main(final String[] args) {
		startMenu();
	}

	// setup menu
	static void startMenu() {
		JPanel menu = new JPanel();

		JLabel productList = new JLabel("<html>");
		JButton contBtn = new JButton("Continue...");

		class inputPanel {

			JPanel frame = new JPanel();
			JButton addBtn = new JButton("Add");

			JTextField nameField = new JTextField();
			JFormattedTextField priceField = new JFormattedTextField(new DecimalFormat("#.##"));
			JFormattedTextField amountField = new JFormattedTextField(new DecimalFormat("#"));

			// storage variables
			String name = "xx04";
			double price = -0.1f;
			int amount = -1;

			public inputPanel() {
				{ // input events

					// nameField settings
					nameField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
					nameField.setColumns(12);
					nameField.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							name = priceField.getText();
						}
					});

					// priceField settings
					priceField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
					priceField.setColumns(8);
					priceField.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							price = Double.parseDouble(priceField.getText());
							priceField.setText("");
						}
					});

					// amountField settings
					amountField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
					amountField.setColumns(5);
					amountField.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							price = Double.parseDouble(amountField.getText());
						}
					});

					addBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							name = nameField.getText();
							price = Double.parseDouble(priceField.getText());
							amount = Integer.parseInt(amountField.getText());
							if (name != "xx04" && price != -0.1f && amount != -1 && products.size() < 10) {
								products.add(new Product(name, price, amount));
								String productName = "Name: " + name + " | Price: " + price + 
										" | Inventory: " + amount;
								String txt = productList.getText() + "<p>" + productName + "</p>";
								productList.setText(txt);
							}
						}
					});
				}

				// frame settings
				frame.setLayout(new FlowLayout());
				frame.setBackground(new Color(150, 200, 200));
				frame.setPreferredSize(new Dimension(WINDOW_WIDTH, 40));
				frame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
				frame.add(new JLabel("Product Name: "));
				frame.add(nameField);
				frame.add(new JLabel("Product Price: $"));
				frame.add(priceField);
				frame.add(new JLabel("Inventory: "));
				frame.add(amountField);
				frame.add(addBtn);
			}
		}

		// continue button settings
		contBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				salesMenu();
			}
		});

		// component size settings
		productList.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT / 5 * 3));
		productList.setHorizontalAlignment(2);
		contBtn.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT / 5));

		// creating menu and adding components
		menu.setBackground(new Color(100, 200, 200));
		menu.add(new inputPanel().frame, JPanel.TOP_ALIGNMENT);
		menu.add(productList, JPanel.CENTER_ALIGNMENT);
		menu.add(contBtn, JPanel.BOTTOM_ALIGNMENT);
		menu.add(new JLabel(""), JPanel.BOTTOM_ALIGNMENT);
		Main.wndw.setContent(menu);
	}

	// main menu during sale
	static void salesMenu() {
		// variables
		JPanel menu = new JPanel();
		JPanel title = new JPanel();
		JLabel titleText = new JLabel("<html><h1>SALE TALLIES</h1></html>");
		title.add(titleText);
		title.setPreferredSize(new Dimension(700, 100));
		menu.add(title, Component.TOP_ALIGNMENT);
		menu.setBackground(new Color(100, 200, 200));
		JPanel content = new JPanel();

		// content settings
		content.setPreferredSize(new Dimension(700, 350));
		title.setBackground(new Color(100, 200, 200));

		try {
			int layoutRows = 1;
			if(products.size() > 5)
				layoutRows = 2;
				
			content.setLayout(new GridLayout(layoutRows, 5));
		} catch (Exception e) {
			content.setLayout(new GridLayout(1, 1));
		}

		// add products to list of products
		for (Product item : products)
			itemList.add(new SalesItem(item));

		// adds product windows to menu
		for (SalesItem i : itemList)
			content.add(i.menu);

		JPanel exitPanel = new JPanel();
		JButton exitBtn = new JButton("Reset");
		JButton contBtn = new JButton("Continue");

		// event for reset button
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				itemList = new ArrayList<>();
				products = new ArrayList<>();
				Main.startMenu();
			}
		});

		contBtn.setBackground(Color.red);
		// method for continue button
		contBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitMenu();
			}
		});

		// add buttons to exit panel
		exitPanel.add(exitBtn);
		exitPanel.add(contBtn);
		exitPanel.setPreferredSize(new Dimension(700, 50));
		// add panels to menu
		menu.add(content);
		menu.add(exitPanel);
		Main.wndw.setContent(menu);
	}

	static void exitMenu() {
		JPanel menu = new JPanel();
		JLabel total = new JLabel();
		JButton exitBtn = new JButton("Exit");
		double profit = 0f;
		for (SalesItem i : itemList)
			profit += i.profitNum;

		// exit button settings
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wndw.exit();
			}
		});
		total.setText("<html><h1><b>Total Expected Profit: $" + rounding.round(profit, 2) + "</b></h1></html>");
		total.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT / 5));
		// menu settings
		menu.setBackground(new Color(100, 200, 200));
		menu.add(total, JPanel.TOP_ALIGNMENT);

		JPanel totalsPane = new JPanel();
		totalsPane.setLayout(new GridLayout(itemList.size() + 1, 0));
		totalsPane.setBackground(new Color(100, 200, 200));
		totalsPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT / 5 * 3));
		totalsPane.add(new JLabel("<html><h2><b>Individual Profits:</b></h2></html>"));
		for (SalesItem i : itemList) {
			String txt = "<html><p><i>" + i.name + " Total: $" + i.profitNum + "</i></p><html>";
			totalsPane.add(new JLabel(txt));
		}
		menu.add(totalsPane, JPanel.LEFT_ALIGNMENT);
		menu.add(exitBtn, JPanel.BOTTOM_ALIGNMENT);
		wndw.setContent(menu);
	}
}

//structure for sale item
class SalesItem {
	JPanel menu = new JPanel();
	JLabel itemName = new JLabel("Missing Item Name");
	JLabel soldAmountDisplay = new JLabel("Total Sold: 0");
	JLabel amountLeft = new JLabel();
	JButton itemSoldBtn = new JButton("We Sold One");

	// profit calculator variables
	JLabel profit = new JLabel("Total: ");
	int soldCount = 0;

	public String name = "";
	public double profitNum = 0f;

	public SalesItem(Product product) {
		name = product.Name;
		
		amountLeft.setText("Inventory: " + product.Amount);
		itemSoldBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				soldCount++;
				soldAmountDisplay.setText("Total Sold: " + soldCount);
				profitNum = product.Price * soldCount;
				profit.setText("Total: $" + product.Price * soldCount);
				product.Amount--;
				amountLeft.setText("Inventory: " + product.Amount);
				
			}
		});

		itemName.setText("<html><h3>" + product.Name + "</h3></html>");

		// menu settings
		menu.setLayout(new GridLayout(7, 0));
		menu.setBackground(new Color(200, 100, 100));
		menu.setBorder(BorderFactory.createEtchedBorder());

		// adding items to menu
		menu.add(itemName, Component.CENTER_ALIGNMENT); // 1
		menu.add(new JLabel("Price: " + product.Price));// 2
		menu.add(new JLabel()); // 3
		menu.add(soldAmountDisplay, Component.CENTER_ALIGNMENT); // 4
		menu.add(amountLeft);
		menu.add(profit); // 5
		menu.add(itemSoldBtn, Component.BOTTOM_ALIGNMENT); // 6
	}
}

class Product {
	String Name;
	double Price;
	int Amount;

	public Product(String name, double price, int amount) {
		Name = name;
		Price = price;
		Amount = amount;
	}
}

class rounding {
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}