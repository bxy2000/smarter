import {Component, Inject, Optional} from '@angular/core';
import {Router} from '@angular/router';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {NzModalService} from 'ng-zorro-antd';
import {ITokenService, DA_SERVICE_TOKEN} from '@delon/auth';
import {ReuseTabService} from '@delon/abc';
import {StartupService} from '@core';
import {LoginService} from "@core/login/login.service";

@Component({
  selector: 'passport-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class UserLoginComponent {

  constructor(
    fb: FormBuilder,
    modalSrv: NzModalService,
    private router: Router,
    @Optional()
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
    private startupSrv: StartupService,
    private loginService: LoginService,
  ) {
    this.form = fb.group({
      username: [null, [Validators.required]],
      password: [null, Validators.required],
      rememberMe: [true],
    });
    modalSrv.closeAll();
  }

  // #region fields

  get username() {
    return this.form.controls.username;
  }

  get password() {
    return this.form.controls.password;
  }

  get rememberMe() {
    return this.form.controls.rememberMe;
  }

  form: FormGroup;
  error = '';
  loading = false;

  submit() {
    this.error = '';
    this.username.markAsDirty();
    this.username.updateValueAndValidity();
    this.password.markAsDirty();
    this.password.updateValueAndValidity();
    if (this.username.invalid || this.password.invalid) {
      return;
    }

    // 默认配置中对所有HTTP请求都会强制 [校验](https://ng-alain.com/auth/getting-started) 用户 Token
    // 然一般来说登录请求不需要校验，因此可以在请求URL加上：`/login?_allow_anonymous=true` 表示不触发用户 Token 校验
    this.loading = true;
    this.loginService.login({
      username: this.username.value,
      password: this.password.value,
      rememberMe: true
    }).subscribe((token: string) => {
        // console.log("token" + token);
        this.loading = false;
        const user = {
          token,
          name: this.username.value,
          email: `admin@gilight.com@qq.com`,
          id: 10000,
          time: +new Date(),
        }
        // 清空路由复用信息
        this.reuseTabService.clear();
        // 设置用户Token信息
        this.tokenService.set(user);
        // 重新获取 StartupService 内容，我们始终认为应用信息一般都会受当前用户授权范围而影响
        this.startupSrv.load().then(() => {
          let url = this.tokenService.referrer!.url || '/';
          if (url.includes('/passport')) {
            url = '/';
          }
          this.router.navigateByUrl(url);
        });
      },
      (error) => {
        this.loading = false;
        this.error = '用户名密码错误!';
      });
  }
}
