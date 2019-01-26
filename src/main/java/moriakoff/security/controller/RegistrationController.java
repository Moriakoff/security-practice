package moriakoff.security.controller;

import moriakoff.security.dto.RegistrationRequest;
import moriakoff.security.entity.User;
import moriakoff.security.entity.type.RoleType;
import moriakoff.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;

@RestController
public class RegistrationController {

    @Autowired
    UserRepository userRepository;


    @PostMapping(value = "/registration")
    public void registration(@RequestBody RegistrationRequest request) {
        if (userRepository.existsByLogin(request.login)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This login " + request.getLogin() + " " +
                    "already in used");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Passwords don't same");
        }

        User user = User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .roleType(new HashSet <>())
                .build();

        user.getRoleType().add(RoleType.USER);

        userRepository.save(user);
    }

}
