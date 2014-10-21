import tools.progressTracker;


public class VRDLauncher {
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
