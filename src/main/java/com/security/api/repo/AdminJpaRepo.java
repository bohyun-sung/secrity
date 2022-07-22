package com.security.api.repo;

import com.security.api.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminJpaRepo extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUid(String email);
}
