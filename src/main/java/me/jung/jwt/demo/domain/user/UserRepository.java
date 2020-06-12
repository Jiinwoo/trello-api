package me.jung.jwt.demo.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(Email email);
//
//    Optional<User> findByUsernameOrEmail(String username, Email email);
//
//    List<User> findByIdIn(List<Long> userIds);
//
//    Optional<User> findByUsername(String username);
//
//    Boolean existsByUsername(String username);
//
//    Boolean existsByEmail(String email);

}
