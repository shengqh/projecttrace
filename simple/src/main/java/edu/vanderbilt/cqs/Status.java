package edu.vanderbilt.cqs;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Status {
	public static final Integer FAILED = -1;
	public static final Integer PROCESSING = 1;
	public static final Integer PENDING = 2;
	public static final Integer FINISHED = 3;

	private static Map<Integer, String> statusMap;

	public static Map<Integer, String> getStatusMap() {
		if (statusMap == null) {
			statusMap = new LinkedHashMap<Integer, String>();
			statusMap.put(FAILED, "failed");
			statusMap.put(PROCESSING, "processing");
			statusMap.put(PENDING, "pending");
			statusMap.put(FINISHED, "finished");
		}
		return statusMap;
	}
}
