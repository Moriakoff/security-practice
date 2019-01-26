package moriakoff.security.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {

    public String login;

    private String password;

    private String confirmPassword;
}
