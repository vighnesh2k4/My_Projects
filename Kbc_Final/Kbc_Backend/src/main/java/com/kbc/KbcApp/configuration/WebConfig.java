package com.kbc.KbcApp.configuration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
    
    @Value("http://10.129.240.233:8080")
    private String addressBasedUrl;
    
    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
    
    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(addressBasedUrl).build();
    }
}
