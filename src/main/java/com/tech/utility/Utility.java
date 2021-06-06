package com.tech.utility;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class Utility {

	public static String getSiteUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		return url.replace(request.getServletPath(), "");
	}
}
