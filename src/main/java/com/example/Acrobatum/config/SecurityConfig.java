package com.example.Acrobatum.config;

import com.example.Acrobatum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/registration").permitAll()
                .antMatchers( "/logIn").permitAll()
                .antMatchers("/product/selectedProduct").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/client/**").hasAnyRole("ADMIN", "CASHIER")
                .antMatchers("/employee/**").hasAnyRole("ADMIN", "HR")
                .antMatchers("/product/**").hasAnyRole("ADMIN", "STOREKEEPER")
                .antMatchers("/provider/**").hasAnyRole("ADMIN", "STOREKEEPER")
                .antMatchers("/basket/**").hasAnyRole("ADMIN", "USER", "CASHIER")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/logIn")
                .defaultSuccessUrl("/success", true);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}
