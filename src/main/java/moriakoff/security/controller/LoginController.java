package moriakoff.security.controller;

import moriakoff.security.dto.LoginRequest;
import moriakoff.security.dto.LoginResponse;
import moriakoff.security.dto.RoleAddingRequest;
import moriakoff.security.entity.User;
import moriakoff.security.entity.UserSession;
import moriakoff.security.repository.UserRepository;
import moriakoff.security.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@Transactional
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSessionRepository userSessionRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        User user = userRepository.findUserByLoginAndPassword(loginRequest.getLogin(), loginRequest.getPassword());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login or password incorrectly");
        }

        UserSession userSession = UserSession.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .isValid(true)
                .build();

        userSessionRepository.save(userSession);

        return new LoginResponse(userSession.getToken());
    }


    @GetMapping("/logout/")
    public void logout(@RequestHeader("Authorization") String token) {

        UserSession session = userSessionRepository.findUserSessionByToken(token);
        session.setValid(false);
        userSessionRepository.save(session);
    }

    @PostMapping("/add-role")
    public void addRoleToUser(@RequestBody RoleAddingRequest request) {
        User user = userRepository.findUserByLogin(request.getLogin());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user doesn't exist");
        }

        user.getRoleType().add(request.getRoleType());

        userRepository.save(user);
    }

}
