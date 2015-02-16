package com.feisystems.bham.web;

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.feisystems.bham.web.filter.gzip.GZipServletFilter;

/**
 * Verifies Gzip filter unit tests.
 */
public final class GZipServletFilterTest extends TestCase {

	private GZipServletFilter filter;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockFilterChain filterChain; 

	@Before
	protected void setUp() throws Exception {
		filter = new GZipServletFilter();
		response = new MockHttpServletResponse();
		filterChain = new MockFilterChain();
	}

	@Test
	public void testGzipFilter() throws IOException, ServletException {

		request = new MockHttpServletRequest();
		request.addHeader("Accept-Encoding", "gzip");
		filter.doFilter(request, response, filterChain);
		assertTrue(filterChain.getResponse().getOutputStream().toString().contains("GZipServletOutputStream"));
	}

	@Test
	public void testGzipFilterNoEncoding() throws IOException, ServletException {
		request = new MockHttpServletRequest();
		filter.doFilter(request, response, filterChain);
		assertFalse(filterChain.getResponse().getOutputStream().toString().contains("GZipServletOutputStream"));
	}

	
	@After
	protected void destroy() throws Exception {
		filter = null;
		request = null;
		response = null;
		filterChain = null;
	}

}