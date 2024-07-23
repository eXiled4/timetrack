package com.time.dev.timetrack.init;

import com.time.dev.timetrack.model.Project;
import com.time.dev.timetrack.model.RoleAdmin;
import com.time.dev.timetrack.model.RoleUser;
import com.time.dev.timetrack.model.Timesheet;
import com.time.dev.timetrack.repository.ProjectRepository;
import com.time.dev.timetrack.repository.RoleAdminRepository;
import com.time.dev.timetrack.repository.RoleUserRepository;
import com.time.dev.timetrack.repository.TimesheetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleUserRepository roleUserRepository;
    private final RoleAdminRepository roleAdminRepository;
    private final ProjectRepository projectRepository;
    private final TimesheetRepository timesheetRepository;

    public DataInitializer(RoleUserRepository roleUserRepository,
                           RoleAdminRepository roleAdminRepository,
                           ProjectRepository projectRepository,
                           TimesheetRepository timesheetRepository) {
        this.roleUserRepository = roleUserRepository;
        this.roleAdminRepository = roleAdminRepository;
        this.projectRepository = projectRepository;
        this.timesheetRepository = timesheetRepository;
    }

    @Override
    public void run(String... args) {
        RoleAdmin admin = new RoleAdmin();
        admin.setName("Admin Name");
        admin.setEmail("admin@example.com");
        admin.setPosition("Administrator");
        roleAdminRepository.save(admin);

        Project project = new Project();
        project.setName("Project A");
        project.setDescription("Description of Project A");
        project.setStatus("Active");
        project.setAdmin(admin);
        projectRepository.save(project);

        RoleUser user = new RoleUser();
        user.setName("User Name");
        user.setEmail("user@example.com");
        user.setPosition("Developer");
        user.setRoleAdmin(admin);
        user.setProject(project);
        roleUserRepository.save(user);

        Timesheet timesheet = new Timesheet();
        timesheet.setDate(new Date());
        timesheet.setHours(8);
        timesheet.setComments("Worked on feature X");
        timesheet.setRoleUser(user);
        timesheet.setRoleAdmin(admin);
        timesheetRepository.save(timesheet);
    }
}
