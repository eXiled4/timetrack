package com.time.dev.timetrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonManagedReference
    private Set<RoleUser> users;

    @OneToMany(mappedBy = "roleAdmin")
    @JsonManagedReference
    private Set<Timesheet> timesheets;

    @OneToMany(mappedBy = "admin")
    @JsonManagedReference
    @JsonIgnoreProperties("roleAdmin")
    private Set<Project> projects;

}
