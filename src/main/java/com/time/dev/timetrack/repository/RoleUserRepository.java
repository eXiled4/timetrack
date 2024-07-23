package com.time.dev.timetrack.repository;

import com.time.dev.timetrack.model.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
}