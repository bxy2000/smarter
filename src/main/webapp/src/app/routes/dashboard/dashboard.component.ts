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
  id = -1;
  height = 800;
  videoUrl: SafeResourceUrl;
  // url = 'https://news.qq.com/zt2020/page/feiyan.htm#/global';
  url1 = 'http://114.115.156.157/share.html#share/dashboard?shareInfo=DA1C3E7DAF7EC46FDC39861F361C78B7517FC6CEF4F1ED425FFF75A2CB7527D3ABD4F0725AF12462EE9485E57D523401B09C93B97BDD1DBD576A52C8C1E8F736EB7B974BC585D40D5BB3F4AD96DB7D2BB7F447349327E9309C2587C6BFA979361456B5FBCAF282D3E95BEF815198FB04DA4096A8E8077930A64DC2338AE692C399A61F03801710C5C8947648890D7B57467D6467E95908F935FC68B27F526066AF67AAEC6684E91A5CB7A44C50854437EDAC80457B509C9B6C27D976884D98BE0DB3C9D385703093D02C3957E7B477533BA10212A7648799018407A92CE0FA7B0AB0217E3BBA54BCCCD78551A975834535A8E5F091997B7009D87289F08A7C3C&type=widget';
  url2 = 'https://news.qq.com/zt2020/page/feiyan.htm#/global';

  url = this.url1;

  outHeight = '0px';

  constructor(
    private http: _HttpClient,
    private reuseTabService: ReuseTabService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit() {
    this.reuseTabService.title = '首页';

    this.route.queryParams.subscribe(p => {
      // this.id = p.id;
      this.url = p.id == 1 ? this.url1 : this.url2;
      this.height = +p.height + 40;
      this.videoUrl = this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
      this.outHeight = this.height + 'px';

      console.log(p.id + " ****************" + p.height);

      // fromEvent(window, 'resize').subscribe(($event) => {
      //
      //   this.outHeight = this.height + 'px';
      // });
    });
  }
}
