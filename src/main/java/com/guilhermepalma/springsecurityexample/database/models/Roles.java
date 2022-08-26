package com.guilhermepalma.springsecurityexample.database.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"roles\"")
public class Roles {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

}
