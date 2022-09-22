package com.guilhermepalma.springsecurityexample.database.repositories;

import com.guilhermepalma.springsecurityexample.database.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Optional<User> findByIdOrUsernameOrName(UUID id, String username, String name);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByUsername(String username);

    @EntityGraph(attributePaths = "roles")
    List<User> findWithRolesByUsernameNotNull();
}
