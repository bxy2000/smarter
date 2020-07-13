import {Component, OnInit} from '@angular/core';
import {_HttpClient} from '@delon/theme';
import {ReuseTabService} from '@delon/abc';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {ActivatedRoute} from '@angular/router';
import {MenuService} from "@core/service/menu.service";

@Component({
  selector: 'app-bi',
  templateUrl: './bi.component.html',
  styles: []
})
export class BiComponent implements OnInit {
  videoUrl: SafeResourceUrl;
  outHeight = '0px';

  constructor(private http: _HttpClient,
              private reuseTabService: ReuseTabService,
              private sanitizer: DomSanitizer,
              private route: ActivatedRoute,
              private menuService: MenuService
              ) {
  }

  ngOnInit() {
    const id = this.route.snapshot.params.id;
    this.menuService.find(id).subscribe( res=>{
      this.reuseTabService.title = res.body.text;
      this.videoUrl = this.sanitizer.bypassSecurityTrustResourceUrl(res.body.menuLink);
      this.outHeight = res.body.menuHeight + 'px';
    });
  }
}
