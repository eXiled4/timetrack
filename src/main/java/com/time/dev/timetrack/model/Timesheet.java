package com.time.dev.timetrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Timesheet {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private int hours;

    private String comments;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "role_user_id")
    private RoleUser roleUser;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "role_admin_id")
    private RoleAdmin roleAdmin;

}
