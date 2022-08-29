package com.guilhermepalma.springsecurityexample.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * A Notation {@link EnableGlobalMethodSecurity} é utilizada para ser possivel fazer o controle de acesso dentro de cada {@link org.springframework.web.bind.annotation.RestController}
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Permite somente as Requests Autorizadas, faz o controle de Acesso (Autorizção) e desabilita o CSFR
        return http.httpBasic().and().authorizeRequests()
                // Permite todas as requests GET do Endpoint
                .antMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**").permitAll().anyRequest().authenticated().and().csrf().disable().build();
    }

}
