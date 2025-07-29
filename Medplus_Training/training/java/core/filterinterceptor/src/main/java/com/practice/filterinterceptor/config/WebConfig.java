package com.practice.filterinterceptor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.practice.filterinterceptor.interceptors.LoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private LoggingInterceptor loggingInterceptor;
	
	@Autowired
	public WebConfig(LoggingInterceptor loggingInterceptor) {
		this.loggingInterceptor=loggingInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingInterceptor)
			.addPathPatterns("/test/**");
	}
}
