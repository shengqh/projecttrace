package edu.vanderbilt.cqs;

import java.util.ArrayList;
import java.util.List;

public abstract class Status {
	public static final String PENDING = "pending";
	public static final String PROCESSING = "processing";
	public static final String FAILED = "failed";
	public static final String FINISHED = "finished";

	private static List<String> statusList;

	public static List<String> getStatusList() {
		if (statusList == null) {
			statusList = new ArrayList<String>();
			statusList.add(PENDING);
			statusList.add(PROCESSING);
			statusList.add(FAILED);
			statusList.add(FINISHED);
		}
		return statusList;
	}
}
