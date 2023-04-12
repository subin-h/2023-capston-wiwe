package com.capston.wiwe.repository;

import com.capston.wiwe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
    List<User> findByReportedIsTrue();
}
