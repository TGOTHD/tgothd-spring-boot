package com.tgothd.tgothd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author: ShrJanLan
 * @date: 2023/6/15 15:17
 * @description:
 */
@Configuration
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("tgothd");
        users.createUser(User.withUsername("tgothd").password(password).roles("admin").build());
        return users;
    }

    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

}
