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

}
