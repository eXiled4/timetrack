package com.time.dev.timetrack.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference("roleAdmin-user")
    private Set<RoleUser> users;

    @OneToMany(mappedBy = "roleAdmin")
    @JsonManagedReference("admin-timesheet")
    private Set<Timesheet> timesheets;

    @OneToMany(mappedBy = "admin")
    @JsonManagedReference("admin-project")
    private Set<Project> projects;
}
