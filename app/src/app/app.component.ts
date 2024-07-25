import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MatToolbar} from "@angular/material/toolbar";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MatToolbar, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TimeTrackr';
}
