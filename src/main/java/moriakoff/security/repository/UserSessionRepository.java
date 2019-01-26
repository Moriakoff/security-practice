package moriakoff.security.repository;

import moriakoff.security.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession,Long> {

    UserSession findUserSessionByUser_Login(String login);

    UserSession findUserSessionByToken(String token);
}
