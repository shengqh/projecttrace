package edu.vanderbilt.cqs.bean;

import java.util.List;

public final class BeanUtils {
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
}
