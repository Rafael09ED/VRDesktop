package displayInput;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.sun.jna.platform.win32.WinDef.HWND;

// working on a functioning window previewer - probably should have created classes using extends, but :P - rafael
public class HWNDWindowManager {
	
	
	private Dimension appSize;
	private Dimension windowSpace;
	private int HWNDDisplayWindowIndex;
	private VRDWindowInitOnce windowCatcher;
	
	Dimension displaySize; // Dimensions for displaying the window
	ArrayList<HWND> HWNDWindowsHandles; // |VALID| window handles
	
	//Dimension processedWindowSpace;
	//Dimension processedAppSize;
	

	
	public HWNDWindowManager() { // constructor for window manager
		windowCatcher = new VRDWindowInitOnce(this);
		appSize = new Dimension(1,1);
		HWNDDisplayWindowIndex = -1;
		displaySize = new Dimension(1,1);
		HWNDWindowsHandles = new ArrayList<HWND>();
	}
	public int getWindowIndex(){
		return HWNDDisplayWindowIndex;
	}

	public void updateAppSize(int widthIn, int heightIn) {
		appSize.setSize(widthIn, heightIn);
		updateDisplaySize();
		
	}

	public void updateWindowSpace(Dimension newNindowSpace) {
		// processedWindowSpace = new Dimension(windowSpace);

		windowSpace = newNindowSpace;
		updateDisplaySize();

	}
	public void setDisplayWindow(int indexIn){
		HWNDDisplayWindowIndex = indexIn-1;
		if (indexIn == 0) {
			return;
		}
		windowCatcher.setWindow(HWNDWindowsHandles.get(HWNDDisplayWindowIndex));
	}
	
	private void updateDisplaySize() {
		
		if (HWNDDisplayWindowIndex == -1) {
			return;
		}
		System.out.println("For the window" + HWNDTools.getWindowTitle(HWNDWindowsHandles.get(HWNDDisplayWindowIndex)));
		System.out.println("Window Size is: " + windowSpace);
		System.out.println("App Size is   : " + appSize);
		
		// create int variables for logic following, consolidated get requests, and casting
		int WindowWidth = 	(int) windowSpace.getWidth();
		int WindowHeight = 	(int) windowSpace.getHeight(); 
		
		int AppWidth =		(int) appSize.getWidth(); 
		int AppHeight = 	(int) appSize.getHeight(); 
		
		if ( WindowWidth < AppWidth && WindowHeight < AppHeight ) {
			// if it is bigger than the space provided : Move to front due to most probable position
			if (AppWidth - WindowWidth  > AppHeight - WindowHeight) {
				displaySize.setSize( (int) AppWidth * (WindowHeight/((double) AppHeight)), WindowHeight); System.out.println("1");
				
			} else {
				displaySize.setSize( WindowWidth, (int) AppHeight * (WindowWidth/((double) AppWidth)) ); System.out.println("2");
				
			}
			
		} else if (WindowWidth >= AppWidth && WindowHeight < AppHeight) { // if window height is smaller then the app height
			displaySize.setSize( (int) AppWidth * (WindowHeight/((double) AppHeight)), WindowHeight); System.out.println("3");
			
		} else if (WindowWidth < AppWidth && WindowHeight >= AppHeight) { // if window width is smaller then the app width 
			displaySize.setSize( WindowWidth, (int) AppHeight * (WindowWidth/((double) AppWidth)) ); System.out.println("4");
			
		} else {
			 // if it is less then both windowHeight and windowWidth
			displaySize.setSize(AppWidth, AppHeight); System.out. println("5");
 
		}
		System.out.println("DisplaySize is :" + displaySize.toString());
		//processedWindowSpace = new Dimension(windowSpace);
		//processedAppSize = new Dimension(appSize);
	}
	public BufferedImage getNewFrame(){
		return windowCatcher.getNewFrame();
	}

	public DefaultListModel getListModel() {
		ArrayList<HWND> AllHWNDWindowsHandles = HWNDTools.getOpenWindowsHandels(); // Use HWNDTools to get handles of open windows
		DefaultListModel listModelTemp = new DefaultListModel(); // create a default list model
		
		listModelTemp.addElement("None"); // First Element is none so no window will show
		
		
		for (int i = 1; i < AllHWNDWindowsHandles.size(); i++) {
			
			System.out.println("trying " + AllHWNDWindowsHandles.get(i-1));
			
			if (VRDWindowInitOnce.isValid(AllHWNDWindowsHandles.get(i-1))) {
				listModelTemp.addElement(HWNDTools.getWindowTitle(AllHWNDWindowsHandles.get(i-1)));
				HWNDWindowsHandles.add(AllHWNDWindowsHandles.get(i-1));
			} else {
				//System.out.println("I am going to break");
			}

			
			// System.out.println(HWNDTools.getWindowTitle(HWNDWindowsHandles.get(i)));

		}
		return listModelTemp;
	}
	

	public int getWidth() {
		return displaySize.width;
	}
	public int getHeight() {
		return displaySize.height;
	}
}
