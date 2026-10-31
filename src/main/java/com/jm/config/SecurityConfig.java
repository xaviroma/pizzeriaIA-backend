package com.jm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CorsConfigurationSource corsConfigurationSource)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
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

                        // Gestión de productos y categorías: ADMIN
                        .requestMatchers("/productos/**", "/categorias/**")
                        .hasRole("ADMIN")

                        // Gestión general de clientes: ADMIN
                        .requestMatchers(HttpMethod.GET, "/clientes")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/clientes/**")
                        .hasRole("ADMIN")

                        // Pedidos: ADMIN puede consultar todos
                        .requestMatchers(HttpMethod.GET, "/pedidos")
                        .hasRole("ADMIN")

                        // Crear pedidos: CLIENTE o ADMIN
                        .requestMatchers(HttpMethod.POST, "/pedidos")
                        .hasAnyRole("CLIENTE", "ADMIN")

                        // El resto requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origin:http://localhost:5173}") String allowedOrigin) {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(allowedOrigin));
        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );
        configuration.setAllowedHeaders(
                List.of("Authorization", "Content-Type")
        );
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}