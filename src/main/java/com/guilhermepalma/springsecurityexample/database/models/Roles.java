package com.guilhermepalma.springsecurityexample.database.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "\"TB_ROLES\"")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@SqlResultSetMapping(
//        name = "rolesEntityMapping",
//        classes = @ConstructorResult(
//                targetClass = Roles.class,
//                columns = {@ColumnResult(name = "id", type = UUID.class), @ColumnResult(name = "name", type = RoleName.class)}
//        )
//)
public class Roles implements GrantedAuthority, Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;

    //    @Enumerated(EnumType.STRING)
    @Id
    @Column(nullable = false, length = 50)
    private String name;
//    private RoleName name;

    @Override
    public String getAuthority() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.name.equals(((Roles) obj).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("Authority{ name=%s }", name);
    }
}
