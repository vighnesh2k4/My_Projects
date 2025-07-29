package com.kbc.KbcApp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class LoggingFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		Long startTime=System.currentTimeMillis();
		chain.doFilter(request, response);
		Long endTime=System.currentTimeMillis();
		System.out.println("Time taken for the URI : "+req.getRequestURI()+" is "+(endTime-startTime)+" milli seconds.");
	}

}
