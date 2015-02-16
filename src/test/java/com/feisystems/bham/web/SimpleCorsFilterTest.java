package com.feisystems.bham.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.feisystems.bham.web.SimpleCORSFilter;

import junit.framework.TestCase;

/**
 * Provides Simple CORS filter unit tests.
 */
public final class SimpleCorsFilterTest extends TestCase {

	private SimpleCORSFilter filter;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockFilterChain filterChain; 

	@Before
	protected void setUp() throws Exception {
		filter = new SimpleCORSFilter();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		filterChain = new MockFilterChain();
		
	}

	@Test
	public void testCORSFilter() throws IOException, ServletException {

		filter.doFilter(request, response, filterChain);

		Collection<String> headers =  response.getHeaderNames();
		headers.contains("Access-Control-Allow-Origin");
		headers.contains("Access-Control-Allow-Methods");
		headers.contains("Access-Control-Max-Age");
		headers.contains("Access-Control-Allow-Headers");
		
		Object allowedMethods = response.getHeaderValue("Access-Control-Allow-Methods");
		assertTrue(allowedMethods.toString().equals("POST, GET, PUT, OPTIONS, DELETE"));

		Object allowedOrgin = response.getHeaderValue("Access-Control-Allow-Origin");
		assertTrue(allowedOrgin.toString().equals("*"));
		
		Object maxAge = response.getHeaderValue("Access-Control-Max-Age");
		assertTrue(maxAge.toString().equals("3600"));
		
		Object allowedHeaders = response.getHeaderValue("Access-Control-Allow-Headers");
		assertTrue(allowedHeaders.toString().contains("Content-Type"));
		
	}

	@After
	protected void destroy() throws Exception {
		filter = null;
		request = null;
		response = null;
		filterChain = null;
		
	}

}