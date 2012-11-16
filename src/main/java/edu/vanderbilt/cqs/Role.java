package edu.vanderbilt.cqs;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Role {
	public static final Integer ADMIN = 1000;
	public static final Integer ROLE_VANGARD_BUDGET_USER = 100;
	public static final Integer VANGARD_USER = 10;
	public static final Integer OBSERVER = 1;
	public static final Integer NONE = 0;
	
	private static Map<Integer, String> roleMap;

	public static Map<Integer, String> getRoleMap() {
		if (roleMap == null) {
			roleMap = new LinkedHashMap<Integer, String>();
			roleMap.put(Role.OBSERVER, "ROLE_OBSERVER");
			roleMap.put(Role.VANGARD_USER, "ROLE_VANGARD_USER");
			roleMap.put(Role.ROLE_VANGARD_BUDGET_USER, "ROLE_VANGARD_BUDGET_USER");
			roleMap.put(Role.ADMIN, "ROLE_ADMIN");
		}
		return roleMap;
	}
}
