package x_old;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.ScrollPane;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

import com.sun.jna.platform.win32.WinDef.HWND;

import displayInput.HWNDTools;
import displayInput.VRDWindowInitOnce;

import java.awt.FlowLayout;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JScrollPane;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

// I Am going to have to reorganize all this code because it does what it is suppose to, just not when I want it! 
// WindowSizeCalc needs to be verified
// simplify, consolidate, comment

public class windowSelector_OLD extends JFrame {

	private JPanel contentPane;
	private JPanel windowPanel;
	// private String[] values;
	private DefaultListModel listModel;
	private ArrayList<HWND> HWNDWindowsHandles;
	private VRDWindowInitOnce windowCatcher;
	private Thread displayThread;
	//private Dimension windowDisplaySize;
	private WindowSizeCalulator wsCalculator;
	private Dimension appSize;
	private Dimension displaySize;
	private HWND currentHWND;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		windowSelector_OLD frame = new windowSelector_OLD();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public windowSelector_OLD() {
		appSize = new Dimension(1,1);
		wsCalculator = new WindowSizeCalulator(getSize(), appSize);
		displaySize = wsCalculator.getDisplaySize();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 542);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		windowCatcher = new VRDWindowInitOnce();

		windowPanel = new JPanel();
		FlowLayout fl_windowPanel = (FlowLayout) windowPanel.getLayout();
		fl_windowPanel.setAlignment(FlowLayout.RIGHT);
		contentPane.add(windowPanel);

		listModel = updateList();
		JList list = new JList(listModel);

		list.addListSelectionListener(new ListSelectionListener() { 	
			public void valueChanged(ListSelectionEvent arg0) {
				
				int index = -1;
				if (arg0.getValueIsAdjusting()) {
					index = arg0.getFirstIndex();
				} else {
					index = arg0.getLastIndex();
				}

				if (listModel.get(index).equals("None")) {
					displayWindow(null);
					return;
				}
				displayWindow(HWNDWindowsHandles.get(index-1));
				wsCalculator.updateAppSize(windowCatcher.getDimension());
				wsCalculator.updateWindowSpace(new Dimension(getSize().width - scrollPane.getWidth(), getHeight()));

			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.setMinimumSize(getMinimumSize());
		contentPane.setMaximumSize(getMaximumSize());
		scrollPane = new JScrollPane(list);
		contentPane.add(scrollPane, BorderLayout.WEST);
		//windowPanel.show();
		
		wsCalculator.updateWindowSpace(new Dimension(getSize().width - scrollPane.getWidth(), getHeight()));
		
		
		// adds resize display calculator 

		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				//if (currentHWND != null) {
					wsCalculator.updateWindowSpace(new Dimension(getSize().width - scrollPane.getWidth(), getHeight()));
				//}
			}
		});
	}

	public void displayWindow(HWND hWndIn) {
		if (hWndIn == null) {
			windowPanel.removeAll();
			windowPanel.validate();
			currentHWND = null;
			return;
		}
		
		if (displayThread == null) {
			createDisplayThread();
			
		}
		if (windowCatcher.getWindow() == hWndIn) {
			return;
		}
		
		windowCatcher.setWindow(hWndIn);
		wsCalculator.updateAppSize(windowCatcher.getDimension());
	}
	private void createDisplayThread(){
		
		displayThread = new Thread() { // Create an instance of an anonymous
			// inner class that extends Thread
			@Override
			public void run() { // Override run() to specify the running
				// behaviors
				boolean stop = false;
				while (true) {
					if (stop)
						break;

					Graphics g = getGraphics();
					
					g.drawImage(windowCatcher.getNewFrame(), scrollPane.getWidth()+5, 5, (int) displaySize.getWidth(), (int)displaySize.getHeight(), null);
					windowPanel.validate();
					
					
					
					// Suspend itself and yield control to other threads for
					// the specified milliseconds
					// Also provide the necessary delay
					try {
						sleep(100); // milliseconds
					} catch (InterruptedException ex) {
					}
				}
			}

		};
	displayThread.start();
	}

	public DefaultListModel updateList() {
		HWNDWindowsHandles = HWNDTools.getOpenWindowsHandels();
		DefaultListModel listModelTemp = new DefaultListModel();
		listModelTemp.addElement("None");
		for (int i = 0; i < HWNDWindowsHandles.size(); i++) {
			if (!VRDWindowInitOnce.isValid(HWNDWindowsHandles.get(i))) {
				break;
			}
			if (VRDWindowInitOnce.isValid(HWNDWindowsHandles.get(i))) {
				listModelTemp.addElement(HWNDTools
						.getWindowTitle(HWNDWindowsHandles.get(i)));
				// System.out.println(HWNDTools.getWindowTitle(HWNDWindowsHandles.get(i)));
			}

		}

		return listModelTemp;

	}

}
