import { Component, OnInit } from '@angular/core';
import {Project} from "../model/project";
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import {MatLine, MatNativeDateModule} from '@angular/material/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import {NgForOf, NgIf, SlicePipe} from "@angular/common";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatList, MatListItem} from "@angular/material/list";
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";

@Component({
  selector: 'app-project-new',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatIconModule,
    MatNativeDateModule,
    MatTooltipModule,
    RouterLink,
    NgIf,
    SlicePipe,
    MatListItem,
    MatList,
    MatCardContent,
    MatCardTitle,
    MatCard,
    MatLine,
    NgForOf
  ],
  templateUrl: './project-new.component.html',
  styleUrl: './project-new.component.css'
})
export class ProjectNewComponent implements OnInit{
  project: Project = new Project();
  projects: Project[] = [];
  feedback: any = {};
  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient) {}
  ngOnInit() {
    this.http.get<Project[]>('/api/projects').subscribe({
      next: (projects) => {
        this.projects = projects;
        this.feedback = {};
      },
      error: (err) => {
        this.feedback = {type: 'warning', message: 'Error loading projects: ' + err.message};
        console.error('Failed to load projects:', err);
      }
    });
  }

  save() {
    this.http.post('/api/projects/', this.project).subscribe({
      next: () => {
        this.feedback = {type: 'success', message: 'Project successfully created!'};
        setTimeout(() => {
          this.router.navigate(['/projects']);
        }, 1000);
        this.router.navigate(['/projects']).then();
      },
      error: () => {
        this.feedback = {type: 'error', message: 'Error creating group'};
      }
    });
  }

  cancel() {
    this.router.navigate(['/projects']).then();
  }

}
