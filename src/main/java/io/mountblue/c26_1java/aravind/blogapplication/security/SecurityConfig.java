package io.mountblue.c26_1java.aravind.blogapplication.security;

import io.mountblue.c26_1java.aravind.blogapplication.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           LoginSuccessHandler loginSuccessHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authConfig ->
                        authConfig
                                .requestMatchers("/blog-application/newpost", "/blog-application/savepost",
                                        "/blog-application/editpost", "/blog-application/deletepost",
                                        "blog-application/api/createpost", "blog-application/api/updatepost",
                                        "blog-application/api/deletepost")
                                    .hasAnyRole("AUTHOR", "ADMIN")
                                .requestMatchers("/blog-application/", "/blog-application/showpost",
                                        "/blog-application/savecomment", "/blog-application/register/**",
                                        "/blog-application/api/**", "/blog-application/access-denied",
                                        "/css/**", "/error")
                                    .permitAll()
                                .anyRequest().authenticated()
                ).formLogin(form ->
                        form
                                .loginPage("/blog-application/login")
                                .loginProcessingUrl("/blog-application/authenticateUser")
                                .successHandler(loginSuccessHandler)
                                .permitAll()
                ).logout(logout ->
                        logout
                                .logoutSuccessUrl("/blog-application/")
                                .permitAll()
                ).exceptionHandling(exceptionConfig ->
                        exceptionConfig
                                .authenticationEntryPoint((request, response, authException) ->
                                        response.sendError(HttpStatus.UNAUTHORIZED.value()))
                                .accessDeniedPage("/blog-application/access-denied")
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
