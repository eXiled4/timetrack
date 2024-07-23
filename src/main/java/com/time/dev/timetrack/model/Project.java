package com.time.dev.timetrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @JoinColumn(name = "admin_id")
    private RoleAdmin admin;


    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private Set<RoleUser> users;

}