import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class SnekMain {
	/**
	 * @param args
	 * Sets various elements of the window, such as its name, size and close operation.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Snek");
		//ImageIcon img = new ImageIcon();
		//frame.setIconImage(img.getImage());
		frame.setContentPane(new SnekPanel());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(SnekPanel.WIDTH, SnekPanel.HEIGHT));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
