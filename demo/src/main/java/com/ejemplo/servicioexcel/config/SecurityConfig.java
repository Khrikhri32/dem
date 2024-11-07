package com.ejemplo.servicioexcel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF si es necesario para APIs REST
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**").permitAll() // Permite acceso sin autenticación a rutas de autenticación
                        .requestMatchers("/api/usuarios/**").authenticated() // Requiere autenticación para rutas de usuarios
                        .anyRequest().authenticated() // Asegura que cualquier otra solicitud esté autenticada
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura la política de sesión para APIs REST
                );

        return http.build(); // Devuelve la configuración de seguridad
    }
}
