
package com.feisystems.bham.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;

import com.feisystems.bham.config.CsrfTokenGeneratorFilter;

/**
 * Provides CSRF filter unit tests.
 */
public final class CsrfTokenGeneratorFilterTest extends TestCase {

	private CsrfTokenGeneratorFilter filter;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockFilterChain filterChain; 
	
	@Before
	protected void setUp() throws Exception {

		filter = new CsrfTokenGeneratorFilter();
		request = new MockHttpServletRequest();
		
		response = new MockHttpServletResponse();
		filterChain = new MockFilterChain();

		CsrfToken csrfToken = mock(CsrfToken.class);
		request.setAttribute("_csrf", csrfToken);

		when(csrfToken.getHeaderName()).thenReturn("X-XSRF-HEADER");
        when(csrfToken.getParameterName()).thenReturn("X-XSRF-PARAM");
        when(csrfToken.getToken()).thenReturn("234213545");

	}

	@Test
	public void testCSRFFilter() throws IOException, ServletException {
		
		filter.doFilter(request, response, filterChain);
		
		Object header = response.getHeaderValue("X-CSRF-HEADER");
		assertTrue(header.toString().equals("X-XSRF-HEADER"));

		Object param = response.getHeaderValue("X-CSRF-PARAM");
		assertTrue(param.toString().equals("X-XSRF-PARAM"));

		Object token = response.getHeaderValue("X-CSRF-TOKEN");
		assertTrue(token.toString().equals("234213545"));
		
	}

	@After
	protected void destroy() throws Exception {
		filter = null;
		request = null;
		response = null;
		filterChain = null;
	}

}