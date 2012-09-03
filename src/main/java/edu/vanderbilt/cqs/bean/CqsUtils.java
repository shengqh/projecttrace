package edu.vanderbilt.cqs.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CqsUtils {
	private static Map<Integer, String> roleMap;

	public static Integer getNextTaskIndex(List<? extends ITask> items) {
		int result = 1;
		for (ITask task : items) {
			if (task.getTaskIndex() >= result) {
				result++;

			}
		}
		return result;
	}

	public static Double getTotalPeopleTime(List<? extends ITask> items) {
		double result = 0.0;
		for (ITask task : items) {
			result += task.getPeopleTime();
		}
		return result;
	}

	public static Double getTotalMachineTime(List<? extends ITask> items) {
		double result = 0.0;
		for (ITask task : items) {
			result += task.getMachineTime();
		}
		return result;
	}

	public static Map<Integer, String> getRoleMap() {
		if (roleMap == null) {
			roleMap = new LinkedHashMap<Integer, String>();
			roleMap.put(Role.OBSERVER, "ROLE_OBSERVER");
			roleMap.put(Role.USER, "ROLE_USER");
			roleMap.put(Role.MANAGER, "ROLE_MANAGER");
			roleMap.put(Role.ADMIN, "ROLE_ADMIN");
		}
		return roleMap;

	}
}
