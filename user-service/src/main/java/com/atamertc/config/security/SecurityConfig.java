package com.atamertc.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUserFilter jwtUserFilter;

    private static final String[] WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/v1/user/find-all",
            "/api/v1/user/find-all-user-elastic",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable(); //CSRF: bir login istegi icin belirli bir value degeri belirlenir ve bu value degerine sahip olmayan istekler engellenir.
        httpSecurity.authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .anyRequest().authenticated();
        //Her seferinde SecurityContexti sifirlar
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtUserFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


}
