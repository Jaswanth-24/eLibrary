package com.elibrary.eLibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

   @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/api/auth/**",     
                "/api/books/**",
                "/h2-console/**",
                "/**"               // static HTML, CSS, JS
            ).permitAll()
            .anyRequest().authenticated()
        )
        .headers(headers -> headers
            .frameOptions(frame -> frame.disable())
        );

    return http.build();
}
}