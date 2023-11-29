package io.mountblue.c26_1java.aravind.blogapplication.service;

import io.mountblue.c26_1java.aravind.blogapplication.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    void save(User user);

    boolean isAdmin(Authentication userAuthentication);
}
