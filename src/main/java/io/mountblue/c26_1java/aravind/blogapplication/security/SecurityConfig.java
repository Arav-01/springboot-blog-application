package io.mountblue.c26_1java.aravind.blogapplication.security;

import io.mountblue.c26_1java.aravind.blogapplication.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder plainTextPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}0")
                .roles("AUTHOR")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}0")
                .roles("AUTHOR")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}0")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, admin);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(plainTextPasswordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, CustomAuthenticationSuccessHandler loginHandler)
            throws Exception {
        httpSecurity
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/blog-application/", "blog-application/showpost", "/css/**")
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
                );

        return httpSecurity.build();
    }
}
