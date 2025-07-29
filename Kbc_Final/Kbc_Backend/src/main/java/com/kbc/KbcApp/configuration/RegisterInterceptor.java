package com.kbc.KbcApp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kbc.KbcApp.interceptors.LoggingInterceptor;

@Configuration
public class RegisterInterceptor implements WebMvcConfigurer {
	
	@Autowired
	private LoggingInterceptor loggingInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingInterceptor).addPathPatterns("/api/questions/**");
	}

}
