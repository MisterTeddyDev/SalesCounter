package io.kaeljohnston.sales;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window {
	JFrame frame = new JFrame("Set the title");
	Container contentPane = frame.getContentPane();
	JFrame exitPane = new JFrame("WARNING");

	public Window(final Dimension windowSize) {

		contentPane.setPreferredSize(windowSize);

		contentPane.setLayout(new GridLayout(0, 1));
		frame.setBackground(Color.red);
		frame.setTitle("Sales Tracking");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	void setContent(final JPanel newPanel) {
		contentPane.removeAll();
		contentPane.add(newPanel);
		frame.validate();
		frame.setVisible(true);
	}

	void exit() {
		JPanel panel = new JPanel();
		JButton yes = new JButton("Quit");
		JButton no = new JButton("Cancel");

		exitPane.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// button settings
		yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitPane.setVisible(false);
			}
		});

		// panel settings
		panel.setBackground(Color.RED);
		panel.setLayout(new GridLayout(3, 2));
		panel.add(new JLabel("Are you sure you want to quit?"));
		for (int i = 0; i < 3; i++)
			panel.add(new JLabel());
		panel.add(no);
		panel.add(yes);
		exitPane.add(panel);
		exitPane.pack();
		exitPane.setVisible(true);
	}
}
