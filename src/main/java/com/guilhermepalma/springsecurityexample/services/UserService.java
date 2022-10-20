package com.guilhermepalma.springsecurityexample.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.guilhermepalma.springsecurityexample.database.models.User;
import com.guilhermepalma.springsecurityexample.database.repositories.UserRepository;
import com.guilhermepalma.springsecurityexample.dto.exceptions.ConflictException;
import com.guilhermepalma.springsecurityexample.dto.exceptions.NotFoundException;
import com.guilhermepalma.springsecurityexample.utis.Utils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    public UserService(UserRepository userRepository, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    public User createUser(User user) throws Exception {
        if (Utils.isNull(user)) {
            throw new ConflictException("user can't be null");
        } else if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("user [%s] already exists", user.getUsername());
        }

        checkUserCredential(user);

        user.setPassword(Utils.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    private void checkUserCredential(User user) {
        if (!Utils.isNullOrEmpty(user.getRoles())) {
            List<Roles> databaseRoles = rolesRepository.findAll();

            if (!Utils.isNullOrEmpty(databaseRoles)) {
                Set<Roles> oldUserRole = user.getRoles();

                Set<Roles> newUserRoles = new HashSet<>();

                for (Roles role : oldUserRole) {
                    Roles updatedRole = databaseRoles.stream()
                            .filter(v -> v.getName().equals(role.getName())).findFirst().orElse(role);
                    newUserRoles.add(updatedRole);
                }

                user.setRoles(newUserRoles);
            }
        }
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

    public String generateUserJWT(User userRequest) throws NotFoundException {
        User user = userRepository.findByIdOrUsername(userRequest.getId(), userRequest.getUsername()).orElseThrow(
                () -> new NotFoundException("not found user [%s] in registers", userRequest.getUsername())
        );

        return JWT.create()
                .withExpiresAt(LocalDateTime.now().plusMinutes(30L).toInstant((ZoneOffset.UTC)))
                .withSubject(user.getUsername()).withClaim("userId", user.getId().toString())
                .sign(Algorithm.HMAC256("gna-r21g-ee-t2r-f2g23g"));
    }
}
