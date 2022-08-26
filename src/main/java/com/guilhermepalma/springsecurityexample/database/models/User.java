package com.guilhermepalma.springsecurityexample.database.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String username;
    private String password;

}
