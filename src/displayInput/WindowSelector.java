package displayInput;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.sun.jna.platform.win32.WinDef.HWND;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import tools.FPSCounter;

public class WindowSelector {

	private JFrame frame; // main frame variable
	private DefaultListModel listModel; // list model for updating jList
	private HWNDWindowManager WindowManager;
	private JPanel appWindowPanel, appListPanel;
	private Thread displayThread; // display thread for drawing graphics
	private JList list;
	private Graphics g;
	
	
	public static void main(String[] args) {

		WindowSelector window = new WindowSelector();
		window.frame.setVisible(true);
		window.frame.validate();

	}

	public WindowSelector() {
		WindowManager = new HWNDWindowManager(); // Initializing Window Manager
		initialize();
	}

	@SuppressWarnings("rawtypes")
	private void initialize() {

		// MainFrame Setup
		frame = new JFrame();
		frame.setBounds(100, 100, 1010, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		listModel = WindowManager.getListModel(); // get model list from window manager
		list = new JList(listModel); // creates JList
		list.addListSelectionListener(new ListSelectionListener() { // action listener for list selection
			public void valueChanged(ListSelectionEvent arg0) {
				int index;
				if (arg0.getValueIsAdjusting()) {
					index = arg0.getFirstIndex();
				} else {
					index = arg0.getLastIndex();
				}
				WindowManager.setDisplayWindow(index);
				if (g != null) {
					g.clearRect(0, 0, appWindowPanel.getWidth(), appWindowPanel.getHeight());
				}
				
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		appWindowPanel = new JPanel(); // creates app Window Panel for displaying
		// appWindowPanel.setBackground(Color.BLACK);
		
		JScrollPane scrollPane = new JScrollPane(list); // creates scrollPane for Jlist
		scrollPane.setMinimumSize(scrollPane.getMinimumSize());

		appListPanel = new JPanel(); // creates App List Panel
		appListPanel.add(scrollPane); // Adds scrollPane to Window
		appListPanel.setMinimumSize(appListPanel.getMinimumSize());

		frame.getContentPane().add(appListPanel, BorderLayout.WEST); // adds App List Panel
		frame.getContentPane().add(appWindowPanel, BorderLayout.CENTER);

		// add frame resize listener
		
		frame.addComponentListener(new ComponentAdapter() { // window builder for resizing window - updates window size change to window manger
			@Override
			public void componentResized(ComponentEvent arg0) {
				WindowManager.updateWindowSpace(new Dimension(appWindowPanel
						.getWidth(), appWindowPanel.getHeight()));
			}
		});
		
		
		createDisplayThread();
	}

	private void createDisplayThread() {

		displayThread = new Thread() {
			@Override
			public void run() {
				boolean stop = false;
				FPSCounter fpscounter = new FPSCounter();
				while (true) {
					if (stop)
						break;
					
					
					
					//Runtime.getRuntime().gc();
					if (WindowManager.getWindowIndex() != -1) {
						g = appWindowPanel.getGraphics();
						//g.clearRect(0, 0, appWindowPanel.getWidth(), appWindowPanel.getHeight());
						//Graphics2D g2d = (Graphics2D) g;
						//g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
						//g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
						//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						fpscounter.fpsTracker();
						g.drawImage(WindowManager.getNewFrame(), 0, 0,(int) WindowManager.getWidth(),(int) WindowManager.getHeight(), null);
						g.dispose();
					} else {
					}
					//System.out.println(fpscounter.getFPS());
					appWindowPanel.validate();
					// frame.validate();
					// Runtime.getRuntime().gc();
					try {
						sleep(10); // should be 100 or <
					} catch (InterruptedException ex) {
					}

				}
			}

		};
		displayThread.start();
	}

}
