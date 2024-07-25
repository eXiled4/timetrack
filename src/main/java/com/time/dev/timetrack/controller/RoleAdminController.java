package com.time.dev.timetrack.controller;

import com.time.dev.timetrack.model.RoleAdmin;
import com.time.dev.timetrack.repository.RoleAdminRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-admins")
public class RoleAdminController {

    @Autowired
    private RoleAdminRepository roleAdminRepository;

    @GetMapping
    public List<RoleAdmin> getAllRoleAdmins() {
        return roleAdminRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleAdmin> getRoleAdminById(@PathVariable Long id) {
        return roleAdminRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/", consumes = {"*/*"})
    public RoleAdmin createRoleAdmin(@Valid @RequestBody RoleAdmin roleAdmin) {
        return roleAdminRepository.save(roleAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleAdmin> updateRoleAdmin(@PathVariable Long id, @Valid @RequestBody RoleAdmin roleAdmin) {
        return roleAdminRepository.findById(id)
                .map(existingRoleAdmin -> {
                    existingRoleAdmin.setName(roleAdmin.getName());
                    existingRoleAdmin.setEmail(roleAdmin.getEmail());
                    existingRoleAdmin.setPosition(roleAdmin.getPosition());
                    return ResponseEntity.ok(roleAdminRepository.save(existingRoleAdmin));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleAdmin(@PathVariable Long id) {
        if (!roleAdminRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        roleAdminRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
