import {Component, OnInit, Inject} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {PasswordService} from "@core/service/password.service";
import {LoginService} from "@core/login/login.service";
import {NzMessageService} from "ng-zorro-antd";
import {Router} from "@angular/router";
import {DA_SERVICE_TOKEN, ITokenService} from '@delon/auth';

@Component({
  selector: 'header-change-password',
  templateUrl: './change-password.component.html',
  styles: [`[nz-form] {
      max-width: 600px;
  }`]
})
export class HeaderChangePasswordComponent implements OnInit {
  // user_name: string = '';
  isVisible = false;
  password = '';
  doNotMatch: string;
  error: string;
  success: string;
  compare: string;
  validateForm: FormGroup;

  // isOkLoading = false;

  constructor(private fb: FormBuilder,
              private loginService: LoginService,
              private passwordService: PasswordService,
              private router: Router,
              private message: NzMessageService,
              @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
  ) {
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      currentPassword: [null, [Validators.required]],
      newPassword: [null, [Validators.required]],
      confirmPassword: [null, [Validators.required, this.confirmationValidator]],
    });
  }

  showModal(): void {
    this.doNotMatch = null;
    this.error = null;
    this.success = null;
    // this.compareFlag = null;
    this.compare = null;
    this.isVisible = true;
  }

  // 确认修改密码
  handleOk(): void {
    this.submitForm();
  }

  // 取消修改密码
  handleCancel(): void {
    this.isVisible = false;
    this.validateForm.reset();
  }


  submitForm(): void {
    this.password = this.validateForm.value.newPassword;
    if (this.validateForm.value.newPassword.length < 4) {
      this.message.error("密码不能少于四位")
    } else if (this.validateForm.value.newPassword !== this.validateForm.value.confirmPassword) {
      this.error = null;
      this.success = null;
      this.doNotMatch = 'ERROR';
      return;
    } else {
      this.doNotMatch = null;
      const currentPassword = this.validateForm.value.currentPassword;
      const newPassword = this.validateForm.value.newPassword;
      this.passwordService.save(newPassword, currentPassword).subscribe(() => {
        this.error = null;
        this.success = 'OK';
        this.doNotMatch = null;
        this.isVisible = false;

        this.tokenService.clear();
        this.router.navigateByUrl(this.tokenService.login_url!);

      }, () => {
        this.success = null;
        this.error = 'ERROR';
        this.doNotMatch = null;
      });
    }
  }

  updateConfirmValidator(): void {
    /** wait for refresh value */
    Promise.resolve().then(() => this.validateForm.controls.confirmPassword.updateValueAndValidity());
  }

  confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return {required: true};
    } else if (control.value !== this.validateForm.controls.newPassword.value) {
      return {confirm: true, error: true};
    }
  }
}
