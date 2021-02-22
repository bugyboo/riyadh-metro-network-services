package com.nervelife.soma.riyadhmetronetworkserver.configuration;

import com.nervelife.soma.riyadhmetronetworkserver.configuration.handlers.CustomAuthFailureHandler;
import com.nervelife.soma.riyadhmetronetworkserver.configuration.handlers.CustomSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class MvcSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService appUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests().antMatchers(HttpMethod.GET, "/**").permitAll()
            .antMatchers("/api/private/admin/**").hasRole("ADMIN")
            .antMatchers("/api/private/**").fullyAuthenticated()
        .and()
            .formLogin()
                .failureHandler(authenticationFailureHandler())
                .successHandler(successHandler())
        .and()
            .headers()
                .frameOptions().disable()
        .and()
            .csrf().disable()
            .cors().configurationSource(configureCors());

    }

    public CorsConfigurationSource configureCors() {
  
        CorsConfiguration cfs = new CorsConfiguration();
        cfs.setAllowCredentials(true);
        cfs.addAllowedOriginPattern("*");
        cfs.addAllowedMethod("OPTIONS");
        cfs.addAllowedMethod("HEAD");
        cfs.addAllowedMethod("GET"); 
        cfs.addAllowedMethod("PUT"); 
        cfs.addAllowedMethod("POST"); 
        cfs.addAllowedMethod("DELETE");      
        cfs.addAllowedMethod("PATCH");        
        cfs.addAllowedHeader("*");
        cfs.addAllowedOrigin("Cookie");
        cfs.addAllowedOrigin("Return-Type");

        return CorsConfigurationSource -> cfs;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
    	return new CustomSuccessHandler();
    }

    @Bean
    public CustomAuthFailureHandler authenticationFailureHandler() {
    	return new CustomAuthFailureHandler();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(appUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
}
