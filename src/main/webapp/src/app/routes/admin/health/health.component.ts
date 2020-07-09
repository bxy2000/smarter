import { Component, OnInit } from '@angular/core';
import {Health, HealthService} from "@core/service/health.service";
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-health',
  templateUrl: './health.component.html',
  styles: []
})
export class HealthComponent implements OnInit {
  health = [];
  constructor(
    private healthService: HealthService
  ) { }

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.healthService.checkHealth().subscribe(
      health => {
        console.log(health);
      },
      (error: HttpErrorResponse) => {
        if(error.status === 503) {
          this.health = error.error;
        }
      }
    )
  }
}
