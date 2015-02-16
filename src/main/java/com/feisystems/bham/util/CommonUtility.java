package com.feisystems.bham.util;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;

public class CommonUtility {
	
	public static Throwable getRoot(Throwable t) {
	    Throwable result = t;

	    while (result.getCause() != null) {
	        result = result.getCause();
	    }

	    return result;
	}
	
	public static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", Constant.APPLICATION_JSON);
		return headers;
	}

	public static Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}	

}
