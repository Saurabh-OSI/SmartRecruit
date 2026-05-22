package com.saurabh.smartrecruit.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurabh.smartrecruit.entity.Role;
import com.saurabh.smartrecruit.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findAllByRoleInOrderByCreatedAtDesc(Collection<Role> roles);
}
