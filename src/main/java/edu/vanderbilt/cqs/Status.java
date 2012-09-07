package edu.vanderbilt.cqs;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Status {
	public static final Integer PENDING = 0;
	public static final Integer PROCESSING = 1;
	public static final Integer FAILED = 2;
	public static final Integer FINISHED = 3;
	public static final Integer CLOSED = 4;

	public static final String[] NAMES = new String[] { "pending",
			"processing", "failed", "finished", "closed" };

	private static Map<Integer, String> statusMap;

	public static Map<Integer, String> getStatusMap() {
		if (statusMap == null) {
			statusMap = new LinkedHashMap<Integer, String>();
			statusMap.put(PENDING, NAMES[PENDING]);
			statusMap.put(PROCESSING, NAMES[PROCESSING]);
			statusMap.put(FAILED, NAMES[FAILED]);
			statusMap.put(FINISHED, NAMES[FINISHED]);
			statusMap.put(CLOSED, NAMES[CLOSED]);

		}
		return statusMap;
	}
}
