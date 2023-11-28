package io.mountblue.c26_1java.aravind.blogapplication.service;

import io.mountblue.c26_1java.aravind.blogapplication.dao.UserRepository;
import io.mountblue.c26_1java.aravind.blogapplication.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password.");
        }

        SimpleGrantedAuthority role = new SimpleGrantedAuthority(
                email.equals("admin@my.org") ? "ROLE_ADMIN" : "ROLE_AUTHOR");

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), List.of(role));
    }
}
