package moriakoff.security.entity;

import lombok.*;
import moriakoff.security.entity.type.RoleType;
import moriakoff.security.entity.type.RoleTypeConverter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    @Convert(converter = RoleTypeConverter.class)
    @ElementCollection(targetClass = RoleTypeConverter.class,fetch = FetchType.EAGER)
    private Set <RoleType> roleType;

    private String password;


}
