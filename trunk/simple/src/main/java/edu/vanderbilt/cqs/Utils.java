package edu.vanderbilt.cqs;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.vanderbilt.cqs.bean.ITask;

public final class Utils {
	public static String md5(String input) {
		if (null == input)
			return null;

		try {
			// Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");

			// Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());

			// Converts message digest value in base 16 (hex)
			return new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return input;
		}
	}

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

	public static String getDateString(Date aDate) {
		if (aDate == null) {
			return "";
		} else {
			return new SimpleDateFormat("yyyy-MM-dd").format(aDate);
		}
	}
}
