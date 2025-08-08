/*package com.vighnesh.mart.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vighnesh.mart.filters.JwtAuthFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

 @Autowired
 private JwtAuthFilter jwtAuthFilter;

 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http.csrf(csrf -> csrf.disable())
     .authorizeHttpRequests(auth -> auth
    		    .requestMatchers("/auth/**").permitAll()
    		    .requestMatchers("/product/getAllProducts").permitAll()
    		    .requestMatchers("/admin/**").hasRole("ADMIN") 
    		    .requestMatchers("/customer/**").hasRole("CUSTOMER") 
    		    .anyRequest().authenticated()
         )
         .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

     return http.build();
 }

 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
     return config.getAuthenticationManager();
 }
}
*/