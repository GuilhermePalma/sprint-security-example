package com.guilhermepalma.springsecurityexample.configs;

import com.guilhermepalma.springsecurityexample.utis.Utils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Classe de Configuração do Spring Security. Extendendo as Configurações da classe depreciada
 * {@link WebSecurityConfigurerAdapter} é possive ter acesso fazer a Autenticação e Autorização da Aplicação.
 * <p>
 * Por mais que seja uma classe Depreciada, existem projetos que ainda utilizam dessa abordagem por estarem trabalhando
 * em versões mais antigas do Spring Boot
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Permite todas as Requests, mesmo sem Autorização:
        // http.httpBasic().and().authorizeRequests().anyRequest().permitAll();

        // Permite somente as Requests Autorizadas e desabilita o CSFR
        http.httpBasic()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Autenticação em memória a utilizar um Usuario e Senha salvos em memoria pelo Spring Boot
        // auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("102030")).roles("ADMIN");

        // Utiliza da Classe UserDetailsService para realizar a autenticação
        auth.userDetailsService(userDetailsService).passwordEncoder(Utils.passwordEncoder());
    }

}
