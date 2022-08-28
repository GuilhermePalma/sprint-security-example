package com.guilhermepalma.springsecurityexample.services;

import com.guilhermepalma.springsecurityexample.database.models.User;
import com.guilhermepalma.springsecurityexample.database.repositories.UserRepository;
import com.guilhermepalma.springsecurityexample.dto.exceptions.ConflictException;
import com.guilhermepalma.springsecurityexample.utis.Utils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("user [%s] already exists", user.getUsername());
        }

        user.setPassword(Utils.passwordEncoder().encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        createdUser.setPassword(null);

        return createdUser;
    }

    public Set<User> getUsers(List<UUID> userId, List<String> username, List<String> name) {
        Set<User> user = new HashSet<>(userRepository.findAll());

        if (!Utils.isNullOrEmpty(userId)) {
            user.removeIf(v -> !userId.contains(v.getId()));
        }

        if (!Utils.isNullOrEmpty(username)) {
            user.removeIf(v -> !username.contains(v.getUsername()));
        }

        if (!Utils.isNullOrEmpty(name)) {
            user.removeIf(v -> !name.contains(v.getName()));
        }

        return user;
    }

}
