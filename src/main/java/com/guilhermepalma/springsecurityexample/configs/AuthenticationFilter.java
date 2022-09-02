package com.guilhermepalma.springsecurityexample.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guilhermepalma.springsecurityexample.database.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Tratará as Requisições ao Endpoint "/login", fazendo a Autenticação do Usuario
 */
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), user.getRoles()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Error in read request body");
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        AuthenticationService.addToken(response, (User) authResult.getPrincipal());
    }

}
