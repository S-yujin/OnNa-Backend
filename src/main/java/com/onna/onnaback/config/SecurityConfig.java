// src/main/java/com/onna/onnaback/config/SecurityConfig.java

package com.onna.onnaback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. CORS 설정을 직접 주입하여 Preflight 및 응답 차단 문제 해결
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 3. HTTP 요청 인가 규칙 설정
            .authorizeHttpRequests(authorize -> authorize
                // ⚠️ (1) 로그인/회원가입 API 허용
                .requestMatchers("/api/auth/**").permitAll()
                
                // 🚀 (2) 클래스 목록 API 허용 (ClassController 경로)
                .requestMatchers("/api/classes/**").permitAll() 
                .requestMatchers("/api/reservations/**").permitAll()
                
                // ⚠️ 그 외 나머지 모든 요청은 반드시 인증이 필요함
                .anyRequest().authenticated()
            );

        return http.build();
    }
    
    // CORS 설정을 위한 Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 🚨 프론트엔드가 실행되는 주소를 정확히 입력해야 합니다. (예시 포트 3000)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000", 
                "http://127.0.0.1:3000",
                "http://localhost:8080"
        )); 
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }
}