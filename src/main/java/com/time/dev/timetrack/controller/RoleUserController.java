package com.time.dev.timetrack.controller;

import com.time.dev.timetrack.model.RoleUser;
import com.time.dev.timetrack.repository.RoleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/role-users")
public class RoleUserController {

    @Autowired
    private RoleUserRepository roleUserRepository;

    @GetMapping
    public List<RoleUser> getAllRoleUsers() {
        return roleUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleUser> getRoleUserById(@PathVariable Long id) {
        return roleUserRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/create", consumes = {"*/*"})
    public RoleUser createRoleUser(@RequestBody RoleUser roleUser) {
        System.out.println("Debug POST");
        return roleUserRepository.save(roleUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleUser> updateRoleUser(@PathVariable Long id, @Valid @RequestBody RoleUser roleUser) {
        return roleUserRepository.findById(id)
                .map(existingRoleUser -> {
                    existingRoleUser.setName(roleUser.getName());
                    existingRoleUser.setEmail(roleUser.getEmail());
                    existingRoleUser.setPosition(roleUser.getPosition());
                    existingRoleUser.setRoleAdmin(roleUser.getRoleAdmin());
                    existingRoleUser.setProject(roleUser.getProject());
                    return ResponseEntity.ok(roleUserRepository.save(existingRoleUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleUser(@PathVariable Long id) {
        if (!roleUserRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        roleUserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
