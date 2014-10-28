import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

import openGL.OpenGLEngine;
import displayInput.WindowInputTest;
import displayInput.WindowInputTester;
import displayInput.WindowSelector;
import tools.progressTracker;

import java.awt.Image;
import java.awt.Label;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VRLMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static progressTracker loadingProgressTracker;
	/**
	 * Launch the application.
	 * @param args 
	 */
	public static void menuManager(progressTracker ProgressTrakerIn) {
		loadingProgressTracker = ProgressTrakerIn; // Not in use, reserved for larger load times
		try {
			VRLMenu frame = new VRLMenu();
			
			frame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			loadingProgressTracker.setProgressStatus("Init Failed", -1); // for the progress tracker
		}
		
	}

	/**
	 * Create the frame.
	 */
	public VRLMenu() {
		// sets the window's icon
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/resources/graphics/logos/Logo2.png")); 
		//sets look and feel to system's
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//cus image icon
		//Image icon = new Image("resources/graphics/logos/Logo-v1.png");
		//setIconImage(icon);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setToolTipText("Menu Text Test");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.window);
		tabbedPane.addTab("Settings", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Testing", null, panel_2, null);
		panel_2.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(0, 0, 529, 491);
		panel_2.add(tabbedPane_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.window);
		tabbedPane_1.addTab("Launch Tests", null, panel_3, null);
		panel_3.setBounds(0, 0, 10, 10);
		panel_3.setLayout(null);
		
		JButton btnNewButton = new JButton("Launch Window Catcher Test");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			      String[] arguments = new String[] {"123"};
			      WindowInputTest.main(arguments);
			}
		});
		btnNewButton.setBounds(10, 11, 183, 23);
		panel_3.add(btnNewButton);
		
		JButton btnLaunchOpenglTest = new JButton("Launch OpenGL Test");
		btnLaunchOpenglTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] arguments = new String[] {"123"};
			      try {
					OpenGLEngine.main(arguments);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLaunchOpenglTest.setBounds(203, 11, 151, 23);
		panel_3.add(btnLaunchOpenglTest);
		
		JButton btnNewButton_1 = new JButton("Open Window Selector");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] arguments = new String[] {"123"};
			    WindowSelector.main(arguments);
			}
		});
		btnNewButton_1.setBounds(10, 45, 183, 23);
		panel_3.add(btnNewButton_1);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Test Settings", null, panel_4, null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		Label label = new Label("VR Desktop Menu");
		label.setFont(new Font("Aaux OfficeMedium", Font.PLAIN, 20));
		panel.add(label);
		
		// Extras
		setResizable(false);
		
		tabbedPane.setSelectedIndex(1);
		tabbedPane_1.setSelectedIndex(0);
	}
}
