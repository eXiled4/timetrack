package com.time.dev.timetrack.controller;

import com.time.dev.timetrack.model.Project;
import com.time.dev.timetrack.model.RoleUser;
import com.time.dev.timetrack.repository.RoleUserRepository;
import com.time.dev.timetrack.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/", consumes = {"*/*"})
    public Project createProject(@Valid @RequestBody Project project) {
        project.setUsers(updateRoleUsers(project.getUsers()));
        return projectRepository.save(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    existingProject.setName(project.getName());
                    existingProject.setDescription(project.getDescription());
                    existingProject.setStatus(project.getStatus());
                    existingProject.setAdmin(project.getAdmin());
                    existingProject.setUsers(updateRoleUsers(project.getUsers()));
                    return ResponseEntity.ok(projectRepository.save(existingProject));
                })
                .orElse(ResponseEntity.notFound().build());
    }


        @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        projectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Set<RoleUser> updateRoleUsers(Set<RoleUser> roleUsers) {
        return roleUsers.stream()
                .map(roleUser -> {
                    if (roleUser.getId() == null || !roleUserRepository.existsById(roleUser.getId())) {
                        return roleUserRepository.save(roleUser); // Save new if not found
                    } else {
                        RoleUser existingRoleUser = roleUserRepository.findById(roleUser.getId()).orElse(null);
                        if (existingRoleUser != null) {
                            existingRoleUser.setName(roleUser.getName());
                            existingRoleUser.setEmail(roleUser.getEmail());
                            existingRoleUser.setPosition(roleUser.getPosition());
                            existingRoleUser.setProject(roleUser.getProject()); // Ensure project linkage
                            return roleUserRepository.save(existingRoleUser);
                        }
                        return null;
                    }
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
