package com.time.dev.timetrack.model;

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
    @JoinColumn(name = "role_user_id")
    private RoleUser roleUser;

    @ManyToOne
    @JoinColumn(name = "role_admin_id")
    private RoleAdmin roleAdmin;

}
