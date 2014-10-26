package displayInput;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.sun.jna.platform.win32.WinDef.HWND;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WindowSelector {

	private JFrame frame; // main frame variable
	private DefaultListModel listModel; // list model for updating jList
	private HWNDWindowManager WindowManager;
	private JPanel appWindowPanel;
	private Thread displayThread; // display thread for drawing graphics
	
	
	public static void main(String[] args) {

		WindowSelector window = new WindowSelector();
		window.frame.setVisible(true);

	}

	public WindowSelector() {
		WindowManager = new HWNDWindowManager(); //Initializing Window Manager
		initialize();
	}

	@SuppressWarnings("rawtypes")
	
	private void initialize() {
		
		//MainFrame Setup
		frame = new JFrame(); 
		frame.addComponentListener(new ComponentAdapter() { // window builder for resizing window - updates window size change to window manger
			@Override
			public void componentResized(ComponentEvent arg0) {
				WindowManager.updateWindowSpace(new Dimension(appWindowPanel.getWidth(), appWindowPanel.getHeight()));
			}
		});
		frame.setBounds(100, 100, 1010, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));	

		
		JPanel appListPanel = new JPanel(); // creates App List Panel
		frame.getContentPane().add(appListPanel, BorderLayout.WEST); // adds App List Panel

		JScrollPane scrollPane = new JScrollPane(); // creates scrollPane for Jlist
		appListPanel.add(scrollPane); // Adds scrollPane to Window
		
		appListPanel.setMinimumSize(appListPanel.getMinimumSize()); // get min and max size for app List Panel
		appListPanel.setMaximumSize(appListPanel.getMaximumSize());
		
		
		listModel = WindowManager.getListModel(); // get model list from window manager
		
		
		
		JList list = new JList(listModel); // creates JList
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.add(list); // Adds Jlist to App List Panel

		appWindowPanel = new JPanel(); // creates app Window Panel for displaying
		frame.getContentPane().add(appWindowPanel, BorderLayout.CENTER);
	}

	

}
