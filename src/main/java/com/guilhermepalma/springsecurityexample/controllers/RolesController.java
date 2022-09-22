package com.guilhermepalma.springsecurityexample.controllers;

import com.guilhermepalma.springsecurityexample.database.models.Roles;
import com.guilhermepalma.springsecurityexample.database.models.User;
import com.guilhermepalma.springsecurityexample.dto.ListView;
import com.guilhermepalma.springsecurityexample.dto.enums.RoleName;
import com.guilhermepalma.springsecurityexample.services.RolesService;
import com.guilhermepalma.springsecurityexample.utis.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class RolesController {

    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

//    @Operation(summary = "Get Roles")
////    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("v1/role")
//    public ListView<Roles> getAllUsers(@RequestParam(required = false) List<UUID> roleIDs,
//                                       @RequestParam(required = false) List<String> roleNames) {
//        try {
//            List<RoleName> roleNamesConverted = null;
//            if (!Utils.isNullOrEmpty(roleNames)) {
//                roleNamesConverted = rolesService.getRolesNameByStrings(roleNames);
//            }
//
//            return new ListView<>(new ArrayList<>(rolesService.getRoles(roleIDs, roleNamesConverted)));
//        } catch (Exception e) {
//            System.out.printf(e.getMessage());
//            return null;
//        }
//    }
}
