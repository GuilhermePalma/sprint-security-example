package com.guilhermepalma.springsecurityexample.database.repositories;

import com.guilhermepalma.springsecurityexample.database.models.Roles;
import com.guilhermepalma.springsecurityexample.dto.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Roles, String> {
//    List<Roles> findAllByNameInOrIdIn(List<RoleName> names, List<UUID> ids);
//
//    @Query(value = "SELECT tb_roles.id, tb_roles.name FROM tb_user  " +
//            "INNER JOIN tb_users_roles ON tb_user.id = tb_users_roles.user_id " +
//            "INNER JOIN tb_roles ON tb_users_roles.role_id = tb_roles.id " +
//            "WHERE tb_user.username =  ?1", nativeQuery = true)
//    List<Roles> findAllUserRolesByUsername(String userUsername);
//
//    Optional<Roles> findRolesByName(RoleName name);
}
