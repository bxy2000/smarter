import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

import {SERVER_API_URL} from '../../app.constants';

@Injectable({providedIn: 'root'})
export class LoginService {
  constructor(private http: HttpClient) {
  }

  // 白向阳：登录
  login(credentials): Observable<any> {

    const data = {
      username: credentials.username,
      password: credentials.password,
      rememberMe: credentials.rememberMe
    };
    return this.http.post(SERVER_API_URL + 'api/authenticate?_allow_anonymous=true', data, {observe: 'response'})
      .pipe(map(authenticateSuccess.bind(this)));

    function authenticateSuccess(resp) {
      const bearerToken = resp.headers.get('Authorization');
      if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
        const jwt = bearerToken.slice(7, bearerToken.length);

        return jwt;
      }

      return "";
    }
  }
}
