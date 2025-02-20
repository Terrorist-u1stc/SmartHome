package com.smarthome.AIHome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF 防护，仅在开发环境或对非浏览器请求禁用
                .csrf(csrf -> csrf.disable())

                // 配置 HTTP 请求的访问权限
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().permitAll()  // 允许所有请求，无需认证
                )

                // 配置身份验证方式，禁用 HTTP Basic 认证，或使用其他方式（如表单登录）
                .httpBasic(httpBasic -> httpBasic.disable());  // 禁用 Basic Authentication

        return http.build();
    }
}
