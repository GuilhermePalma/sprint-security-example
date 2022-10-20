package com.guilhermepalma.springsecurityexample.database.models;

import com.guilhermepalma.springsecurityexample.dto.enums.RoleName;
import com.guilhermepalma.springsecurityexample.utis.Utils;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
//public class Roles implements GrantedAuthority, Serializable {
public class Roles implements GrantedAuthority, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName name;

    @Override
    public String getAuthority() {
        return Utils.isNull(this.getName()) ? null : this.getName().toString();
    }
}
