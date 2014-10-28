package displayInput;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import org.omg.CORBA.INTF_REPOS;

import tools.FPSCounter;

public class MultiCoreWindowInputTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BufferedImage img;
	private static FPSCounter fpsCounter;
	private VRDWindow windowCatcher;
	static MultiCoreWindowInputTest frame;
	JLabel lblNewLabel;

	public static void main(String[] args) {
		fpsCounter = new FPSCounter();

		try {
			frame = new MultiCoreWindowInputTest();
			frame.setVisible(true);
			Thread t = new Thread() { // Create an instance of an anonymous
										// inner class that extends Thread
				@Override
				public void run() { // Override run() to specify the running
									// behaviors
					int count = 0;
					boolean stop = false;
					for (int i = 0; i < 100000; ++i) {
						if (stop)
							break;
						frame.updateVirtual();
						++count;
						// Suspend itself and yield control to other threads for
						// the specified milliseconds
						// Also provide the necessary delay
						try {
							sleep(10); // milliseconds
						} catch (InterruptedException ex) {
						}
					}
				}
			};
			t.start(); // Start the thread. Call back run() in a new thread
			while (true) {

				frame.updateWindow();
				t.interrupt();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create the frame.
	 */
	public MultiCoreWindowInputTest() {
		windowCatcher = new VRDWindow("Steam"); // THIS IS WHERE YOU CHANGE THE
												// PROGRAM TO TAKE THE VIEW OF.
												// IT IS BASED OFF OF WINDOW
												// Title (NOT TSKMGR NAME)
		// Next Step is to get a dropdown of all windows and auto unminimize and
		// store previous state

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1033, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		lblNewLabel = new JLabel("");
		img = windowCatcher.getNewFrame();
		lblNewLabel.setIcon(new ImageIcon(img));
		contentPane.add(lblNewLabel, BorderLayout.WEST);

	}

	private void updateVirtual() {
		img = windowCatcher.getNewFrame();
		FPStracker();
	}

	private void updateWindow() {

		lblNewLabel.setIcon(new ImageIcon(img));
		frame.validate();
		//FPStracker();

	}

	private void FPStracker() {
		fpsCounter.fpsTracker();
		System.out.println(fpsCounter.getAdvFPS());

	}

}
