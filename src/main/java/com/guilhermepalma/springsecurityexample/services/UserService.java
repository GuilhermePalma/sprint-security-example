package com.guilhermepalma.springsecurityexample.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.guilhermepalma.springsecurityexample.database.models.Roles;
import com.guilhermepalma.springsecurityexample.database.models.User;
import com.guilhermepalma.springsecurityexample.database.repositories.RolesRepository;
import com.guilhermepalma.springsecurityexample.database.repositories.UserRepository;
import com.guilhermepalma.springsecurityexample.dto.exceptions.ConflictException;
import com.guilhermepalma.springsecurityexample.dto.exceptions.NotFoundException;
import com.guilhermepalma.springsecurityexample.utis.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RolesService rolesService;
    private final RolesRepository rolesRepository;

    public UserService(UserRepository userRepository, RolesService rolesService, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesService = rolesService;
        this.rolesRepository = rolesRepository;
    }

   /* public CreatedDTO<User> getUserCreatedDTO(Payload<User> users) {
        AtomicLong quantityError = new AtomicLong(0);
        List<String> logErrors = new ArrayList<>();

        Set<RoleName> usersRolesNames = users.getData()
                .stream().map(v -> v.getRoles().stream().map(Roles::getName).collect(Collectors.toSet()))
                .map(v-> v.iterator().next()).collect(Collectors.toSet());
        Set<Roles> rolesList = Collections.synchronizedSet(new HashSet<>(rolesService.getRoles(new ArrayList<>(), new ArrayList<>(usersRolesNames))));

        List<User> usersCreated = users.getData().stream().parallel().map(user -> {
            try {
                return createUser(user, rolesList);
            } catch (Exception e) {
                Long error = quantityError.incrementAndGet();
                logErrors.add(String.format("[%s] - [%s]", error, e.getMessage()));
                throw new RuntimeException(e);
            }
        }).toList();

        CreatedDTO<User> createdDTO = new CreatedDTO<>(usersCreated);
        createdDTO.setErrors(quantityError.get());
        createdDTO.setLogErrors(logErrors);
        return createdDTO;
    }*/

   @Transactional
    public User createUserV2(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("user [%s] already exists", user.getUsername());
        }

        user.setPassword(Utils.passwordEncoder().encode(user.getPassword()));
//        List<Roles> roles = rolesRepository.findAll();
//       for (Roles e : roles) {
//           System.out.println(e.getName());
//       }

        return userRepository.save(user);
   }

    @Transactional(readOnly = true)
    public Set<User> getUsers(List<UUID> userId, List<String> username, List<String> name) {
        Set<User> user = new HashSet<>(userRepository.findWithRolesByUsernameNotNull());

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

    @Transactional(readOnly = true)
    public User getDetailedUser(UUID id, String username) throws NotFoundException {
        return userRepository.findOneWithRolesByUsername(username).orElseThrow(
                () -> new NotFoundException("not found user: id: [%s], username [%s]", id, username)
        );
    }

    public String generateUserJWT(User userRequest) throws NotFoundException {
        User user = userRepository.findByIdOrUsernameOrName(userRequest.getId(), userRequest.getUsername(), null).orElseThrow(
                () -> new NotFoundException("not found user [%s] in registers", userRequest.getUsername())
        );

        return JWT.create()
                .withExpiresAt(LocalDateTime.now().plusMinutes(30L).toInstant((ZoneOffset.UTC)))
                .withSubject(user.getUsername()).withClaim("userId", user.getId().toString())
                .sign(Algorithm.HMAC256("gna-r21g-ee-t2r-f2g23g"));
    }

    //    public User getDetailedUsers(UUID id, String username, String name) throws NotFoundException {
//        User user = userRepository.findByIdOrUsernameOrName(id, username, name).orElseThrow(
//                () -> new NotFoundException("not found user: id: [%s], username [%s]", id, username)
//        );
//
//        user.setRoles(rolesService.getUserRoles(user.getUsername()));
//        return user;
//    }

    /*public User createUser(User user, Set<Roles> rolesSet) throws Exception {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("user [%s] already exists", user.getUsername());
        }

        Set<RoleName> notExistedRole = new HashSet<>();
        for (Roles roles : user.getRoles()) {
            Roles roleDatabase = rolesSet.stream().filter(v -> v.getName().equals(roles.getName())).findFirst().orElse(null);

            if (Utils.isNull(roleDatabase)) notExistedRole.add(roles.getName());
            else roles.setId(roleDatabase.getId());
        }

        if (!Utils.isNullOrEmpty(notExistedRole)) {
            Set<Roles> createdRoles = notExistedRole.stream().map(Roles::new).map(rolesService::createRoles).collect(Collectors.toSet());
            rolesSet.addAll(createdRoles);
        }

        user.setPassword(Utils.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }*/
}
