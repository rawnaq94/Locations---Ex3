package gui;

import javax.swing.JFrame;

/*
 * initiate the main frame
 */
public class MainClass {
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
}
