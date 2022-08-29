package com.guilhermepalma.springsecurityexample.database.repositories;

import com.guilhermepalma.springsecurityexample.database.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {
}
