package edu.vanderbilt.cqs;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModuleType {
	public static final Integer OneSamplePerUnit = 1;
	public static final Integer PerSamplePerUnit = 2;

	private static Map<Integer, String> unitTypeMap;

	public static Map<Integer, String> getModuleTypeMap() {
		if (unitTypeMap == null) {
			unitTypeMap = new LinkedHashMap<Integer, String>();
			unitTypeMap.put(OneSamplePerUnit, "One sample per unit");
			unitTypeMap.put(PerSamplePerUnit, "Per sample per unit");
		}
		return unitTypeMap;
	}

	public static String getString(Integer unitFeeType) {
		if (getModuleTypeMap().containsKey(unitFeeType)) {
			return getModuleTypeMap().get(unitFeeType);
		} else {
			return getModuleTypeMap().get(ModuleType.OneSamplePerUnit);
		}
	}
}
