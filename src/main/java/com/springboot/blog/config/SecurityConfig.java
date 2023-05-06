package com.springboot.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Slf4j
public class SecurityConfig {


    //For encoding the password with BCryp. algo.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // For Basic AuthN mechanism
    @Bean
     SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests((authorize)->
//                        authorize.anyRequest().authenticated()
                authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    //For In-memory AuthN with UserName/Password
    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
//        User.UserBuilder users = User
        UserDetails user = User.builder()
                .username("sushil")
                .password(passwordEncoder().encode("sushil"))
                .roles("USER")
                .build();

        log.info("Password for user {} ", user.getPassword());
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();

        log.info("Password for admin {} ", admin.getPassword());
        return new InMemoryUserDetailsManager(user, admin);
    }

}