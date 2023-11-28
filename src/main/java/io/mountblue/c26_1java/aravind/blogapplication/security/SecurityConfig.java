package io.mountblue.c26_1java.aravind.blogapplication.security;

import io.mountblue.c26_1java.aravind.blogapplication.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, CustomAuthenticationSuccessHandler loginHandler)
            throws Exception {
        httpSecurity
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/blog-application/", "/blog-application/showpost",
                                        "/blog-application/register/**", "/css/**", "/error")
                                    .permitAll()
                                .requestMatchers("/blog-application/newpost")
                                    .hasAnyRole("AUTHOR", "ADMIN")
                                .anyRequest().authenticated()
                ).formLogin(form ->
                        form
                                .loginPage("/blog-application/login")
                                .loginProcessingUrl("/blog-application/authenticateUser")
                                .successHandler(loginHandler)
                                .permitAll()
                ).logout(logout ->
                        logout
                                .logoutSuccessUrl("/blog-application/")
                                .permitAll()
                ).exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return httpSecurity.build();
    }
}
