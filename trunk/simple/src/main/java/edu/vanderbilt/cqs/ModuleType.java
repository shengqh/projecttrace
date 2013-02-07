package edu.vanderbilt.cqs;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModuleType {
	public static final Integer PerSample = 1;
	public static final Integer PerSamplePerUnit = 2;
	public static final Integer PerUnit = 3;

	private static Map<Integer, String> unitTypeMap;

	public static Map<Integer, String> getModuleTypeMap() {
		if (unitTypeMap == null) {
			unitTypeMap = new LinkedHashMap<Integer, String>();
			unitTypeMap.put(PerSample, "Per sample");
			unitTypeMap.put(PerSamplePerUnit, "Per sample per unit");
			unitTypeMap.put(PerUnit, "Per unit");
		}
		return unitTypeMap;
	}

	public static String getString(Integer unitFeeType) {
		if (getModuleTypeMap().containsKey(unitFeeType)) {
			return getModuleTypeMap().get(unitFeeType);
		} else {
			return getModuleTypeMap().get(ModuleType.PerSample);
		}
	}
}
