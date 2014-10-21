package tools;

import java.util.ArrayList;

public class progressTracker {
	String trackerName = null;
	double progressPercent = 0.0;
	String progressStatus = "Initializing";
	ArrayList<String> statusLog = new ArrayList<String>();

	public progressTracker() {
		statusLog.add(progressStatus);
	}

	public progressTracker(String nameIn) {
		trackerName = nameIn;
	}

	public progressTracker(double progIn) {
		progressPercent = progIn;
	}

	public double getProgress() {
		return progressPercent;
	}

	public void setProgress(double progIn) {
		progressPercent = progIn;
	}

	public void setProgressStatus(String statusIn) {
		progressStatus = statusIn;
		statusLog.add(statusIn);
	}

	public void setProgressStatus(String statusIn, double progIn) {
		setProgressStatus(statusIn);
		setProgress(progIn);
	}
	public ArrayList<String> getStatusLog(){
		return statusLog;
	}
}
