package com.guilhermepalma.springsecurityexample.controllers;

import com.guilhermepalma.springsecurityexample.database.models.User;
import com.guilhermepalma.springsecurityexample.dto.CreatedDTO;
import com.guilhermepalma.springsecurityexample.dto.ListView;
import com.guilhermepalma.springsecurityexample.dto.Payload;
import com.guilhermepalma.springsecurityexample.dto.exceptions.NotFoundException;
import com.guilhermepalma.springsecurityexample.services.UserService;
import com.guilhermepalma.springsecurityexample.utis.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(value = "v1/user/create", produces = "application/json")
    public CreatedDTO<User> createUsers(@RequestBody Payload<User> users) throws Exception {
        if (Utils.isNullOrEmpty(users.getData())) {
            throw new NotFoundException("not found values in payload");
        }

//        return userService.getUserCreatedDTO(users);

        AtomicLong quantityError = new AtomicLong(0);
        List<String> logErrors = new ArrayList<>();
        List<User> usersCreated = new ArrayList<>();
        for (User v : users.getData()) {
            try {
                usersCreated.add(userService.createUserV2(v));
            } catch (Exception e) {
                Long error = quantityError.incrementAndGet();
                logErrors.add(String.format("[%s] - [%s]", error, e.getMessage()));
                throw new RuntimeException(e);
            }
        }
//        List<User> usersCreated = users.getData().stream().parallel().map(v -> {
//            try {
//                return userService.createUserV2(v);
//            } catch (Exception e) {
//                Long error = quantityError.incrementAndGet();
//                logErrors.add(String.format("[%s] - [%s]", error, e.getMessage()));
//                throw new RuntimeException(e);
//            }
//        }).toList();

        CreatedDTO<User> createdDTO = new CreatedDTO<>(usersCreated);
        createdDTO.setErrors(quantityError.get());
        createdDTO.setLogErrors(logErrors);

        return createdDTO;
    }

    @Operation(summary = "Get Users")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("v1/user")
    public ListView<User> getAllUsers(@RequestParam(required = false) List<UUID> userId,
                                      @RequestParam(required = false) List<String> username,
                                      @RequestParam(required = false) List<String> name) {
        return new ListView<>(new ArrayList<>(userService.getUsers(userId, username, name)));
    }

//    @Operation(summary = "Get Users Detailed")
////    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("v1/user/detailed")
//    public ListView<User> getAllUsersDetailed(@RequestParam(required = false) UUID userId,
//                                              @RequestParam(required = false) String username,
//                                              @RequestParam(required = false) String name) throws NotFoundException {
//        return new ListView<>(new ArrayList<>(Collections.singleton(userService.getDetailedUsers(userId, username, name))));
//    }

    @Operation(summary = "Get One User Detailed")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("v1/user/detailed")
    public ListView<User> getAllUsersDetailed(@RequestParam(required = false) UUID userId,
                                              @RequestParam(required = false) String username) throws NotFoundException {
        return new ListView<>(new ArrayList<>(Collections.singleton(userService.getDetailedUser(userId, username))));
    }

}

