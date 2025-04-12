package auth_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_Id",length = 100,nullable = false)
    private Long id;

    @Column(name="role_Name",length = 150,nullable = false)
    private String Role;

    @OneToMany(mappedBy = "userRole",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<UserRole> userRoles=new HashSet<UserRole>();

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", Role='" + Role + '\'' +
                '}';
    }
}
