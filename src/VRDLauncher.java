import tools.progressTracker;


public class VRDLauncher {
	//This is the launcher that will manage the whole program, choosing to launch the menu or the program eventually
	@SuppressWarnings("unused")
	private String launcherVersion = "00.00.00.01 pre-alpha";
	
	@SuppressWarnings("unused")
	private String[] devs = {"Rafael09ED","BlueDevil","addtheletters"};
	
	public static void main(String[] args) {
		//Launch Splash Screen
		//Launch Menu
		progressTracker MemuProgress = new progressTracker();
		VRLMenu.menuManager(MemuProgress);
		//Get Progress for Menu and update
		

	}

}
