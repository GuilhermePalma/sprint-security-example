package com.guilhermepalma.springsecurityexample.configs;

import com.guilhermepalma.springsecurityexample.database.models.User;
import com.guilhermepalma.springsecurityexample.database.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Sobrescreve a classe {@link UserDetailsService} para que customize a maneira como será feita as autenticações
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDatabase = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User not found with username [%s]", username)
        ));

        return new org.springframework.security.core.userdetails.User(userDatabase.getUsername(),
                userDatabase.getPassword(), true, true, true, true, userDatabase.getRoles()
        );
    }


}
