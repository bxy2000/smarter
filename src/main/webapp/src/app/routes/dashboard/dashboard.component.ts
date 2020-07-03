import {Component, OnInit} from '@angular/core';
import {_HttpClient} from '@delon/theme';
import {ReuseTabService} from '@delon/abc';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {fromEvent} from 'rxjs';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {

  constructor(
    private reuseTabService: ReuseTabService,
  ) {
  }

  ngOnInit() {
    this.reuseTabService.title = '首页';
  }
}
