package org.kiennguyenfpt.datingapp.configs;

import org.kiennguyenfpt.datingapp.services.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
public class UserDetailsServiceConfig {
    /*
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build());
        return manager;
    }

     */
    /*
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("kiennctqe170207@fpt.edu.vn")
                .password("{noop}password") // Sử dụng {noop} để không mã hóa
                .roles("USER")
                .build());
        return manager;
    }

     */
    /*
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

     */
}
