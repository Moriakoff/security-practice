package moriakoff.security.filter;

import moriakoff.security.entity.User;
import moriakoff.security.entity.UserSession;
import moriakoff.security.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    UserSessionRepository userSessionRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null) {

            UserSession userSession = userSessionRepository.findUserSessionByToken(header);

            if (userSession != null && userSession.isValid()) {

                List <GrantedAuthority> roles = userSession.getUser().getRoleType()
                        .stream()
                        .map(roleType -> roleType.toString())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                Authentication key = new UsernamePasswordAuthenticationToken(
                        userSession.getUser().getLogin(),
                        null,
                        roles);


                SecurityContextHolder.getContext().setAuthentication(key);
                System.out.println(key);
            }
        }


        filterChain.doFilter(request, response);

    }
}
