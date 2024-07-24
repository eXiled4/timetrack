package com.time.dev.timetrack.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private int hours;

    private String comments;

    @ManyToOne
    @JsonBackReference("user-timesheet")
    @JoinColumn(name = "role_user_id")
    private RoleUser roleUser;

}
