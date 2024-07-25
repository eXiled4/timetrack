import { Component } from '@angular/core';
import {Project} from "../model/project";
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [RouterLink, MatButtonModule, MatTableModule, MatIconModule, DatePipe, HttpClientModule],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})
export class ProjectListComponent {
  title = 'Project List';
  loading = true;
  projects: Project[] = [];
  displayedColumns = ['projectTitle', 'description', 'users', 'status', 'actions'];
  feedback: any = {};

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.loading = true;
    this.http.get<Project[]>('api/projects').subscribe((data: Project[]) => {
      this.projects = data;
      this.loading = false;
      this.feedback = {};
    });
  }
  delete(project: Project): void {
    if (confirm(`Are you sure you want to delete ${project.name}?`)) {
      this.http.delete(`api/projects/${project.id}`).subscribe({
        next: () => {
          this.feedback = {type: 'success', message: 'Delete was successful!'};
          setTimeout(() => {
            this.ngOnInit();
          }, 1000);
        },
        error: () => {
          this.feedback = {type: 'warning', message: 'Error deleting.'};
        }
      });
    }
  }
}
