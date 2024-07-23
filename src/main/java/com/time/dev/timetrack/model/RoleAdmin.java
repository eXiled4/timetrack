package com.time.dev.timetrack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class RoleAdmin {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String position;

    @OneToMany(mappedBy = "roleAdmin")
    private Set<RoleUser> users;

    @OneToMany(mappedBy = "roleAdmin")
    private Set<Timesheet> timesheets;

    @OneToMany(mappedBy = "admin")
    private Set<Project> projects;

}