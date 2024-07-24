package com.time.dev.timetrack.model;

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
public class RoleUser {

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

    @ManyToOne
    @JsonBackReference("roleAdmin-user")
    @JoinColumn(name = "role_admin_id")
    private RoleAdmin roleAdmin;

    @ManyToOne
    @JsonBackReference("project-user")
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "roleUser")
    @JsonManagedReference("user-timesheet")
    private Set<Timesheet> timesheets;
}
