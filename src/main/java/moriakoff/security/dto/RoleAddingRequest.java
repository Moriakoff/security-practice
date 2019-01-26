package moriakoff.security.dto;

import lombok.*;
import moriakoff.security.entity.type.RoleType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleAddingRequest {

    private String login;

    private RoleType roleType;
}
