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

  save() {
    const id = this.project.id;
    const method = id ? 'put' : 'post';

    this.http[method](`/api/projects${id ? '/' + id : ''}`, this.project).subscribe({
      next: () => {
        this.feedback = {type: 'success', message: 'Save was successful!'};
        setTimeout(async () => {
          await this.router.navigate(['/projects']);
        }, 3000);
      },
      error: () => {
        this.feedback = {type: 'error', message: 'Try again later, error saving changes'}
      }
    });
  }
  async cancel(){
    await this.router.navigate(['/projects']);
  }

  addUser() {
    this.project.users.push(new User());
  }

  removeUser(index: number){
    this.project.users.splice(index, 1);
  }
}
