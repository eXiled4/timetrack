package com.time.dev.timetrack.controller;

import com.time.dev.timetrack.model.Timesheet;
import com.time.dev.timetrack.repository.TimesheetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {

    @Autowired
    private TimesheetRepository timesheetRepository;

    @GetMapping
    public List<Timesheet> getAllTimesheets() {
        return timesheetRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> getTimesheetById(@PathVariable Long id) {
        return timesheetRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/create", consumes = {"*/*"})
    public Timesheet createTimesheet(@Valid @RequestBody Timesheet timesheet) {
        return timesheetRepository.save(timesheet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Timesheet> updateTimesheet(@PathVariable Long id, @Valid @RequestBody Timesheet timesheet) {
        return timesheetRepository.findById(id)
                .map(existingTimesheet -> {
                    existingTimesheet.setDate(timesheet.getDate());
                    existingTimesheet.setHours(timesheet.getHours());
                    existingTimesheet.setComments(timesheet.getComments());
                    existingTimesheet.setRoleUser(timesheet.getRoleUser());
                    existingTimesheet.setRoleAdmin(timesheet.getRoleAdmin());
                    return ResponseEntity.ok(timesheetRepository.save(existingTimesheet));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimesheet(@PathVariable Long id) {
        if (!timesheetRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        timesheetRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
