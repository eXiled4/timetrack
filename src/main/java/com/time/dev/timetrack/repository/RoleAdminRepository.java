package com.time.dev.timetrack.repository;

import com.time.dev.timetrack.model.RoleAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAdminRepository extends JpaRepository<RoleAdmin, Long> {
}
