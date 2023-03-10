package qs.sukaworkplea.qq.narxoz1963.joins;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "t_users")
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "full_name")
    private String fullname;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;
}
