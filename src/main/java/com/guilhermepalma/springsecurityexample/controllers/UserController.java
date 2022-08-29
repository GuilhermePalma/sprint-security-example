package com.guilhermepalma.springsecurityexample.controllers;

import com.guilhermepalma.springsecurityexample.database.models.User;
import com.guilhermepalma.springsecurityexample.dto.CreatedDTO;
import com.guilhermepalma.springsecurityexample.dto.ListView;
import com.guilhermepalma.springsecurityexample.dto.Payload;
import com.guilhermepalma.springsecurityexample.dto.exceptions.NotFoundException;
import com.guilhermepalma.springsecurityexample.services.UserService;
import com.guilhermepalma.springsecurityexample.utis.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(value = "v1/user/create", produces = "application/json")
    public CreatedDTO<User> createUsers(@RequestBody Payload<User> users) throws Exception {
        if (Utils.isNullOrEmpty(users.getData())) {
            throw new NotFoundException("not found values in payload");
        }

        AtomicLong quantityError = new AtomicLong(0);
        List<String> logErrors = new ArrayList<>();
        List<User> usersCreated = users.getData().stream().parallel().map(v -> {
            try {
                return userService.createUser(v);
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
    }

    @Operation(summary = "Get Users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("v1/user")
    public ListView<User> getAllUsers(@RequestParam(required = false) List<UUID> userId,
                                      @RequestParam(required = false) List<String> username,
                                      @RequestParam(required = false) List<String> name) {
        return new ListView<>(new ArrayList<>(userService.getUsers(userId, username, name)));
    }

}
