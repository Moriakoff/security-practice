package moriakoff.security.repository;

import moriakoff.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByLogin(String login);

    User findUserByLoginAndPassword(String login, String password);

    User findUserByLogin(String login);
}
