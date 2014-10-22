package tools;


public class timeTracker {
	long timeCreated;
	double[] timeSteps;
	int stepPos;
	public timeTracker(int maxArraySizeIn) {
		timeSteps = new double[maxArraySizeIn];
		timeCreated = System.nanoTime();
		stepPos = 0;
	}
	public void nextStep(){
		timeSteps[stepPos] = System.nanoTime();
		stepPos++;
	}
	public double[] timePerStep(){
		double[] timePerStep = new double[timeSteps.length];
		
		timePerStep[0] = (timeSteps[0]-timeCreated)/1000000000.0;
		
		for (int i = 1; i < stepPos; i++) {
			
			timePerStep[i] = (timeSteps[i]-timeSteps[i-1])/1000000000.0;
		}
		
		return timePerStep;
	}
}
