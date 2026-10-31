package com.jm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // Peticiones CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Catálogo público
                        .requestMatchers(HttpMethod.GET,
                                "/productos/**",
                                "/categorias/**").permitAll()

                        // Registro y login
                        .requestMatchers(HttpMethod.POST,
                                "/clientes",
                                "/auth/login").permitAll()

                        // Webhook de Stripe
                        .requestMatchers(HttpMethod.POST,
                                "/webhook/stripe").permitAll()

                        // Todo lo demás requiere JWT
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}