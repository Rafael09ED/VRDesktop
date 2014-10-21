package tools;

public class FPSCounter {
	int frameCounter;
	double timeBefore;
	double timeAfter;
	double lastTime;
	double FPSimmediate;
	double AdvFPS;
	int FramesCount;
	
	public FPSCounter() {
		frameCounter = 0;
		timeBefore = System.nanoTime();
		lastTime = timeBefore;
		FramesCount = 0;
	}
	public void fpsTracker(){ 
		FPSimmediate = 1000000000.0 / (System.nanoTime() - lastTime); //one second(nano) divided by amount of time it takes for one frame to finish
        lastTime = System.nanoTime();
        FramesCount++;
	}
	public double getFPS(){
		return FPSimmediate;
		
	}
	public double getAdvFPS(){
		if (FramesCount == 0) {
			//FramesCount++;
			AdvFPS = FPSimmediate;
			System.err.println("fpsTacker must be use to calculate getAdvFPS");
			return FPSimmediate;
		}
		AdvFPS = ((AdvFPS * FramesCount)+ FPSimmediate)/ (FramesCount+1);
		//FramesCount++;
		return AdvFPS;
		
	}

}
