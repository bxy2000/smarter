import {NzMessageService} from 'ng-zorro-antd';
import {HttpInterceptor, HttpRequest, HttpErrorResponse, HttpHandler, HttpEvent} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Injector, Injectable} from '@angular/core';
import {Router} from '@angular/router';

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {

  constructor(private injector: Injector) {
  }

  get msg(): NzMessageService {
    return this.injector.get(NzMessageService);
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap(
        (event: HttpEvent<any>) => {
        },
        (err: any) => {
          if (err instanceof HttpErrorResponse) {
            if (!(err.status === 401 && (err.message === '' || (err.url && err.url.includes('/api/account'))))) {
              this.errorHandler(err);
            }
          }
        }
      )
    );
  }

  private goTo(url: string) {
    setTimeout(() => this.injector.get(Router).navigateByUrl(url));
  }

  errorHandler(response: HttpErrorResponse): void {
    let i;
    const httpErrorResponse = response;
    switch (httpErrorResponse.status) {
      // connection refused, server not reachable
      case 0:
        // 服务器拒绝访问，后端程序没有开启
        this.dispMessage("无法连接到服务器！");
        break;
      case 401: // 未登录状态码
        this.goTo('/passport/login');
        break;
      case 400:
        // 服务器内容错误，各种异常
        const arr = httpErrorResponse.headers.keys();
        let errorHeader = null;
        let entityKey = null;
        arr.forEach((entry) => {
          if (entry.endsWith('app-error')) {
            errorHeader = httpErrorResponse.headers.get(entry);
          } else if (entry.endsWith('app-params')) {
            entityKey = httpErrorResponse.headers.get(entry);
          }
        });
        if (errorHeader) {
          const entityName = entityKey;
          this.sendMessage(errorHeader, errorHeader, {entityName});
        } else if (httpErrorResponse.error !== '' && httpErrorResponse.error.fieldErrors) {
          const fieldErrors = httpErrorResponse.error.fieldErrors;
          for (i = 0; i < fieldErrors.length; i++) {
            const fieldError = fieldErrors[i];
            // convert 'something[14].other[4].id' to 'something[].other[].id' so translations can be written to it
            const convertedField = fieldError.field.replace(/\[\d*\]/g, '[]');
            const fieldName = convertedField.charAt(0).toUpperCase() +
              convertedField.slice(1);
            this.sendMessage(
              'Error on field "' + fieldName + '"', 'error.' + fieldError.message, {fieldName});
          }
        } else if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
          this.sendMessage(httpErrorResponse.error.message, httpErrorResponse.error.message, httpErrorResponse.error.params);
        } else {
          this.sendMessage(httpErrorResponse.error);
        }
        break;

      case 404:
        this.sendMessage('找不到指定连接！');
        break;
      default:
        if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
          this.sendMessage(httpErrorResponse.error.message);
        } else {
          this.sendMessage(httpErrorResponse.error);
        }
    }
  }

  // 在页面上显示错误信息，同时，控制台也显示
  dispMessage(message: string, key?, data?): void {
    let error = "错误信息:" + message;

    if (key) error += " key:" + key;
    if (data) error += " data:" + data;

    console.log(error);

    if (this.msg !== undefined) {
      this.msg.error(error);
    }
  }

  // 在控制台打印错误信息！
  sendMessage(message: string, key?, data?): void {
    let error = "错误信息:" + message;
    if (key) error += " key:" + key;
    if (data) error += " data:" + data;
    console.log(error);
  }
}
