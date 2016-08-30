package by.it.novik.pojos;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
@Entity
@Table(name="role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    //Идентификатор роли

    private Long idRole;
    @Id
    @GeneratedValue
    @Column(name="id_role")
    @Type(type="long")
    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }
    //Название роли
    private String role;
    @Column (name = "role", length = 50)
    @Type(type = "string")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Role() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        if (idRole != null ? !idRole.equals(role1.idRole) : role1.idRole != null) return false;
        return role != null ? role.equals(role1.role) : role1.role == null;

    }

    @Override
    public int hashCode() {
        int result = idRole != null ? idRole.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + idRole +
                ", role='" + role + '\'' +
                '}' + '\n';
    }
}
