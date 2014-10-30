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

public class WindowInputTest extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	BufferedImage img;
	private static FPSCounter fpsCounter; 
	private VRDWindowInitOnce windowCatcher;
	static WindowInputTest frame;
	private static String windowPassed;
	JLabel lblNewLabel;
	
	
	public static void main(String[] args) {
		fpsCounter = new FPSCounter();
		windowPassed = args[0];
				try {
					frame = new WindowInputTest();
					frame.setVisible(true);
					
					
					while (true) {
						
						frame.updateWindow();
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
	}

	/**
	 * Create the frame.
	 */
	public WindowInputTest() {
		windowCatcher = new VRDWindowInitOnce(windowPassed); // THIS IS WHERE YOU CHANGE THE PROGRAM TO TAKE THE VIEW OF. IT IS BASED OFF OF WINDOW Title (NOT TSKMGR NAME)
		// Next Step is to get a dropdown of all windows and auto unminimize and store previous state
		
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

	private void updateWindow(){
		
		lblNewLabel.setIcon(new ImageIcon(windowCatcher.getNewFrame()));
		frame.validate();
		FPStracker();
	
	}
	
	private void FPStracker(){
		fpsCounter.fpsTracker();
		System.out.println(fpsCounter.getFPS());
		
	}
	

}
