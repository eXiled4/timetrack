package com.time.dev.timetrack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Project {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    private String status;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private RoleAdmin admin;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private RoleUser manager;

    @OneToMany(mappedBy = "project")
    private Set<RoleUser> users;

}