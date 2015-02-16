package com.feisystems.bham.util;

public class RecommendationUtil {
	
	/**
	 * Default constructor is private to prevent instantiation.
	 */
	private RecommendationUtil() {
	}

    public static boolean isOpioid(String socialHistoryCode) {
		if (socialHistoryCode != null && socialHistoryCode.equals("F11.20"))
			return true;
		return false;
    }
}