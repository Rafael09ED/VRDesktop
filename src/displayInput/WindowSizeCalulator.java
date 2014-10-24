package displayInput;

import java.awt.Dimension;

public class WindowSizeCalulator {
	// this is so we can keep the aspect ratio, while saving processing data, and keep things updated
	Dimension windowSpace;
	Dimension processedWindowSpace;
	Dimension appSize;
	Dimension processedAppSize;
	Dimension displaySize;

	public WindowSizeCalulator(Dimension windowSpaceIn,
			Dimension appSizeIn) {
		windowSpace = windowSpaceIn;
		appSize = appSizeIn;
		processedWindowSpace = new Dimension(windowSpace);
		processedAppSize = new Dimension(windowSpaceIn);
		displaySize = new Dimension(1,1);
		updateDisplaySize();
		
	}
	public Dimension getDisplaySize() {
		return displaySize;
	}
	public void checkDisplaySizeValidity(){
		if (processedWindowSpace.equals(windowSpace) && processedAppSize.equals(appSize)) {
			return;
		}
		updateDisplaySize();
	}
	
	public void updateAppSize(Dimension newAppSize){
		processedAppSize = new Dimension(appSize);
		appSize = newAppSize;
		updateDisplaySize();
	}
	
	public void updateWindowSpace(Dimension newNindowSpace){
		processedWindowSpace = new Dimension(windowSpace);
		windowSpace = newNindowSpace;
		updateDisplaySize();
	}
	
	private void updateDisplaySize() {
		System.out.println("Window Size is: " + windowSpace);
		System.out.println("App Size is   : " + appSize);
		int WindowWidth = (int) windowSpace.getWidth();
		int WindowHeight = (int) windowSpace.getHeight();
		
		int AppWidth = (int) appSize.getWidth(); 
		int AppHeight = (int) appSize.getHeight(); 
		
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
			displaySize.setSize(AppWidth, AppHeight); System.out.println("5");
 
		}
		System.out.println("DisplaySize is :" + displaySize.toString());
		processedWindowSpace = new Dimension(windowSpace);
		processedAppSize = new Dimension(appSize);
	}
	
}
