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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleUserRepository roleUserRepository;
    private final RoleAdminRepository roleAdminRepository;
    private final ProjectRepository projectRepository;
    private final TimesheetRepository timesheetRepository;
    private final Date oneDayBefore;
    private final Date twoDaysBefore;

    public DataInitializer(RoleUserRepository roleUserRepository,
                           RoleAdminRepository roleAdminRepository,
                           ProjectRepository projectRepository,
                           TimesheetRepository timesheetRepository) {
        this.roleUserRepository = roleUserRepository;
        this.roleAdminRepository = roleAdminRepository;
        this.projectRepository = projectRepository;
        this.timesheetRepository = timesheetRepository;

        LocalDate oneDay = LocalDate.now().minusDays(1);
        this.oneDayBefore = Date.from(oneDay.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate twoDays = LocalDate.now().minusDays(2);
        this.twoDaysBefore = Date.from(twoDays.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void run(String... args) {
        RoleAdmin admin1 = new RoleAdmin();
        admin1.setName("John Admin");
        admin1.setEmail("johnadmin@example.com");
        admin1.setPosition("Account Executive");
        roleAdminRepository.save(admin1);

        RoleAdmin admin2 = new RoleAdmin();
        admin2.setName("Sally Admin");
        admin2.setEmail("sallyadmin@example.com");
        admin2.setPosition("Delivery Manager");
        roleAdminRepository.save(admin2);

        Project project1 = new Project();
        project1.setName("QLD Health");
        project1.setDescription("CIMHA to unify various MH departments together");
        project1.setStatus("Active");
        project1.setAdmin(admin1);
        projectRepository.save(project1);

        Project project2 = new Project();
        project2.setName("John Deere");
        project2.setDescription("Integration between HR and Payroll system");
        project2.setStatus("Planning");
        project2.setAdmin(admin2);
        projectRepository.save(project2);

        RoleUser user1 = new RoleUser();
        user1.setName("John User");
        user1.setEmail("johnuser@example.com");
        user1.setPosition("Developer");
        user1.setProject(project1);
        roleUserRepository.save(user1);

        RoleUser user2 = new RoleUser();
        user2.setName("Alice User");
        user2.setEmail("alice.second@example.com");
        user2.setPosition("Senior Database Administrator");
        user2.setProject(project1);
        roleUserRepository.save(user2);

        RoleUser user3 = new RoleUser();
        user3.setName("Peter Ball");
        user3.setEmail("peteruser@example.com");
        user3.setPosition("Database Administrator");
        user3.setProject(project2);
        roleUserRepository.save(user3);

        RoleUser user4 = new RoleUser();
        user4.setName("Jane User");
        user4.setEmail("jane.doe@example.com");
        user4.setPosition("Senior Backend Developer");
        user4.setProject(project2);
        roleUserRepository.save(user4);

        Timesheet timesheet1a = new Timesheet();
        timesheet1a.setDate(twoDaysBefore);
        timesheet1a.setHours(8);
        timesheet1a.setComments("Initial Project Setup");
        timesheet1a.setRoleUser(user1);
        timesheetRepository.save(timesheet1a);

        Timesheet timesheet1b = new Timesheet();
        timesheet1b.setDate(oneDayBefore);
        timesheet1b.setHours(8);
        timesheet1b.setComments("Worked on ticket CMHA-9090");
        timesheet1b.setRoleUser(user1);
        timesheetRepository.save(timesheet1b);

        Timesheet timesheet2a = new Timesheet();
        timesheet2a.setDate(twoDaysBefore);
        timesheet2a.setHours(5);
        timesheet2a.setComments("Requirements gathering");
        timesheet2a.setRoleUser(user2);
        timesheetRepository.save(timesheet2a);

        Timesheet timesheet2b = new Timesheet();
        timesheet2b.setDate(oneDayBefore);
        timesheet2b.setHours(7);
        timesheet2b.setComments("Analysis and architecture planning");
        timesheet2b.setRoleUser(user2);
        timesheetRepository.save(timesheet2b);
    }
}
