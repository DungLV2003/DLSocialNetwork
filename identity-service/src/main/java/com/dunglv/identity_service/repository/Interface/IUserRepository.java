package com.dunglv.identity_service.repository.Interface;

import com.dunglv.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
