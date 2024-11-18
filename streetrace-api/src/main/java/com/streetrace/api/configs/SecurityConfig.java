package com.streetrace.api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Настройка безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable() // Отключаем CSRF
                .cors() // Включаем CORS
                .and()
                .authorizeRequests(auth -> auth
                        .requestMatchers("/api/users/**").permitAll()  // Разрешаем доступ без авторизации для этих эндпоинтов
                        .anyRequest().permitAll() // Для остальных требуется авторизация
                )
                .build();
    }

    // Настройка CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Разрешаем доступ ко всем эндпоинтам
                        .allowedOrigins("http://localhost:3000") // Разрешаем только с фронтенда, если он работает на этом домене
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешаем эти методы
                        .allowedHeaders("*") // Разрешаем все заголовки
                        .allowCredentials(true); // Разрешаем отправку cookies (если нужно)
            }
        };
    }
}