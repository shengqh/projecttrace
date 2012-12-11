package edu.vanderbilt.cqs;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Status {
	public static final String PENDING = "Pending";
	public static final String ACTIVE = "Active";
	public static final String CANCELLED = "Cancelled";
	public static final String COMPLETE = "Complete";

	private static Map<String, String> statusMap;

	public static Map<String, String> getStatusMap() {
		if (statusMap == null) {
			statusMap = new LinkedHashMap<String, String>();
			statusMap.put(PENDING, PENDING);
			statusMap.put(ACTIVE, ACTIVE);
			statusMap.put(CANCELLED, CANCELLED);
			statusMap.put(COMPLETE, COMPLETE);
		}
		return statusMap;
	}
}
