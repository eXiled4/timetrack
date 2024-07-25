import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { map, of, switchMap } from 'rxjs';
import {Project} from "../model/project";
import {User} from "../model/user";
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import {ProjectListComponent} from "../project-list/project-list.component";

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

  // save() {
  //   const id = this.project.id;
  //   const method = id ? 'put' : 'post';
  //
  //   this.http[method](`/api/projects${id ? '/' + id : ''}`, this.project).subscribe({
  //     next: () => {
  //       this.feedback = {type: 'success', message: 'Save was successful!'};
  //       setTimeout(async () => {
  //         await this.router.navigate(['/projects']);
  //       }, 2000);
  //     },
  //     error: () => {
  //       this.feedback = {type: 'error', message: 'Try again later, error saving changes'}
  //     }
  //   });
  // }

  save() {
    const id = this.project.id;
    const method = id ? 'put' : 'post';
    const url = `/api/projects${id ? '/' + id : ''}`;

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
        this.feedback = {type: 'success', message: 'Project and user data saved successfully!'};
        setTimeout(async () => {
          await this.router.navigate(['/projects']);
        }, 2000);
      },
      error: error => {
        console.error("Save error:", error);
        this.feedback = {type: 'error', message: 'Error saving project and user data: ' + error.message};
      }
    });
  }


  async cancel(){
    await this.router.navigate(['/projects']);
  }

  addUser() {
    this.project.users.push(new User());
    const newUser = new User();
    this.http.post<User>('/api/users', newUser).subscribe({
      next: (user) => {
        this.project.users.push(user); // Add the user with data returned from the server, including ID
        this.feedback = { type: 'success', message: 'User added successfully' };
      },
      error: (error) => {
        this.feedback = { type: 'error', message: 'Failed to add user: ' + error.message };
      }
    });
  }

  removeUser(index: number){
    this.project.users.splice(index, 1);
    const user = this.project.users[index];
    if (user && user.id) {
      this.http.delete(`/api/users/${user.id}`).subscribe({
        next: () => {
          this.project.users.splice(index, 1); // Only remove the user from the array if the delete was successful
          this.feedback = { type: 'success', message: 'User removed successfully' };
        },
        error: (error) => {
          this.feedback = { type: 'error', message: 'Failed to remove user: ' + error.message };
        }
      });
    } else {
      // If no ID, just remove from the local array (e.g., if it was a new unsaved user)
      this.project.users.splice(index, 1);
    }
  }
}
