package com.feisystems.bham.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.feisystems.bham.util.Constant;

@Component
public class SimpleCORSFilter implements Filter {
	
    /**
     * Enables Cross Origin Resource Sharing 
     */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", Constant.HTTP_DIALET);
		response.setHeader("Access-Control-Max-Age", Constant.MAX_AGE);
		response.setHeader("Access-Control-Allow-Headers", Constant.X_REQUEST_WITH);
		response.setHeader("Access-Control-Allow-Headers", Constant.X_XSRF_TOKEN);
		response.setHeader("Access-Control-Allow-Headers", Constant.CONTENT_TYPE);
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}