package io.mountblue.c26_1java.aravind.blogapplication.dao;

import io.mountblue.c26_1java.aravind.blogapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
