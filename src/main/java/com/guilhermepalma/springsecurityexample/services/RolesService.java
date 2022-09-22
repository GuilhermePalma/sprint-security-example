package com.guilhermepalma.springsecurityexample.services;

import com.guilhermepalma.springsecurityexample.database.models.Roles;
import com.guilhermepalma.springsecurityexample.database.repositories.RolesRepository;
import com.guilhermepalma.springsecurityexample.dto.enums.RoleName;
import com.guilhermepalma.springsecurityexample.utis.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RolesService {

    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

//    public List<Roles> getRoles(List<UUID> roleIDs, List<RoleName> roleNames) {
//        return (!Utils.isNullOrEmpty(roleIDs) || !Utils.isNullOrEmpty(roleNames))
//                ? rolesRepository.findAllByNameInOrIdIn(roleNames, roleIDs)
//                : rolesRepository.findAll();
//    }
//
//
//    public List<RoleName> getRolesNameByStrings(List<String> names) throws Exception {
//        if (Utils.isNullOrEmpty(names)) {
//            throw new IllegalArgumentException("role names can't be null or empty");
//        }
//
//        return names.stream().map(RoleName::valueOf).collect(Collectors.toList());
//    }
//
//    public Roles createRoles(Roles roles) {
//        return rolesRepository.saveAndFlush(roles);
//    }
//
//    public List<Roles> getUserRoles(String username){
//        return rolesRepository.findAllUserRolesByUsername(username);
//    }

}
