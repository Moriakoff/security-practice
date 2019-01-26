package moriakoff.security;

import moriakoff.security.entity.User;
import moriakoff.security.entity.type.RoleType;
import moriakoff.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Component
public class BootstrapMain implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (!userRepository.existsByLogin("Admin")){
            User admin = User.builder()
                    .login("Admin")
                    .password("Admin")
                    .roleType(new HashSet <>())
                    .build();

            admin.getRoleType().add(RoleType.ADMIN);

            userRepository.save(admin);
        }
    }
}
