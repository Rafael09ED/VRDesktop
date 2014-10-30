package displayInput;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

import x_old.VRDWindow;

public class WindowInputTester extends JComponent {

	private static final long serialVersionUID = 1L;
	JFrame frame;
	BufferedImage img;
	private VRDWindow windowCatcher;
	
	public WindowInputTester() {
		// getName
		// set up frame
		// loop the frame updates

		JFrame frame = new JFrame("LIVE FEED FROM NOTEPAD!!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.pack();
		frame.setVisible(true);
		windowCatcher = new VRDWindow("Untitled - Notepad");
	}

	

	public void updateView() {
		img = windowCatcher.getNewFrame();
	}

	public void paint(Graphics g) {

		g.drawImage(img, 50, 50, this);

	}
}

// while looping get other program to return an image from whatever I can
// open notepad and it will work after you fix it
// Original code here:
// http://stackoverflow.com/questions/8288869/change-image-in-jframe
// http://stackoverflow.com/questions/4433994/java-window-image