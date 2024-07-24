import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import {ProjectListComponent} from "./project-list/project-list.component";
import {ProjectEditComponent} from "./project-edit/project-edit.component";

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'projects',
    component: ProjectListComponent
  },
  {
    path: 'project/:id',
    component: ProjectEditComponent
  }
];
