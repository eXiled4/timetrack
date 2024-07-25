import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {lastValueFrom, map, of, switchMap} from 'rxjs';
import {Project} from "../model/project";
import {User} from "../model/user";
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {MatInputModule} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatIconModule} from '@angular/material/icon';
import {MatNativeDateModule} from '@angular/material/core';
import {MatTooltipModule} from '@angular/material/tooltip';

@Component({
  selector: 'app-project-edit',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatIconModule,
    MatNativeDateModule,
    MatTooltipModule,
    RouterLink
  ],
  templateUrl: './project-edit.component.html',
  styleUrl: './project-edit.component.css'
})
export class ProjectEditComponent implements OnInit{
  project!: Project;
  user!: User;
  feedback: any = {};
  usersToDelete: User[] = [];

  constructor(private route: ActivatedRoute, private router: Router,
              private http: HttpClient) {
  }

  ngOnInit() {
    this.route.params.pipe(
      map(p => p['id']),
      switchMap(id => {
        if (id === 'new') {
          return of(new Project());
        }
        return this.http.get<Project>(`api/projects/${id}`);
      })
    ).subscribe({
      next: project => {
        this.project = project;
        this.feedback = {};
      },
      error: () => {
        this.feedback = {type: 'warning', message: 'Try later, error loading'};
      }
    });
  }
  save() {
    const id = this.project.id;
    const method = id ? 'put' : 'post';
    const url = `/api/projects${id ? '/' + id : ''}`;

    // First, delete users that were removed
    const deleteRequests = this.usersToDelete.map(user =>
      lastValueFrom(this.http.delete(`/api/users/${user.id}`))
    );

    Promise.all(deleteRequests).then(() => {
      // Prepare the project object, which includes nested users
      const payload = {
        ...this.project,
        users: this.project.users.map(user => ({
          id: user.id,
          name: user.name,
          email: user.email,
          position: user.position
        }))
      };

      console.log("Sending payload:", payload);
      // Send the entire project object to the backend, including user data
      this.http[method](url, payload).subscribe({
        next: () => {
          console.log("Save successful");
          this.feedback = { type: 'success', message: 'Project and user data saved successfully!' };
          setTimeout(async () => {
            await this.router.navigate(['/projects']);
          }, 2000);
        },
        error: error => {
          console.error("Save error:", error);
          this.feedback = { type: 'error', message: 'Error saving project and user data: ' + error.message };
        }
      });
    }).catch(error => {
      console.error("Error deleting users:", error);
      this.feedback = { type: 'error', message: 'Error deleting users: ' + error.message };
    });
  }

  async cancel(){
    await this.router.navigate(['/projects']);
  }

  addUser() {
    this.project.users.push(new User());
  }

  removeUser(index: number){
    const user = this.project.users[index];
    if (user.id) {
      this.usersToDelete.push(user);
    }
    this.project.users.splice(index, 1);
  }
}
