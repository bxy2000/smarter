import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

import {SERVER_API_URL} from '../../../app.constants';
import {createRequestOption} from '@core/utils/request-util';
import {IUserExtendsVM} from "@core/model/user-extends-vm.model";

@Injectable({providedIn: 'root'})
export class UserExtendsExtService {
  public resourceUrl = SERVER_API_URL + 'api/ext/user-extends';

  constructor(private http: HttpClient) {
  }

  create(userExtends: IUserExtendsVM): Observable<HttpResponse<IUserExtendsVM>> {
    const copy = this.convertDateFromClient(userExtends);
    return this.http
      .post<IUserExtendsVM>(this.resourceUrl, copy, {observe: 'response'})
      .pipe(map((res: HttpResponse<IUserExtendsVM>) => this.convertDateFromServer(res)));
  }

  update(userExtends: IUserExtendsVM): Observable<HttpResponse<IUserExtendsVM>> {
    const copy = this.convertDateFromClient(userExtends);
    return this.http
      .put<IUserExtendsVM>(this.resourceUrl, copy, {observe: 'response'})
      .pipe(map((res: HttpResponse<IUserExtendsVM>) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<HttpResponse<IUserExtendsVM>> {
    return this.http
      .get<IUserExtendsVM>(`${this.resourceUrl}/${id}`, {observe: 'response'})
      .pipe(map((res: HttpResponse<IUserExtendsVM>) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IUserExtendsVM[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserExtendsVM[]>(this.resourceUrl, {params: options, observe: 'response'})
      .pipe(map((res: HttpResponse<IUserExtendsVM[]>) => this.convertDateArrayFromServer(res)));
  }

  resetPassword(login: any): Observable<any> {
    return this.http.post(this.resourceUrl + '/reset-password', login);
  }

  export(req?: any): Observable<Blob> {
    const options = createRequestOption(req);
    return this.http.get(this.resourceUrl + '/export', {params: options, responseType: 'blob'});
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  private convertDateFromClient(userExtends: IUserExtendsVM): IUserExtendsVM {
    const copy: IUserExtendsVM = {...userExtends};
    return copy;
  }

  private convertDateFromServer(res: HttpResponse<IUserExtendsVM>): HttpResponse<IUserExtendsVM> {
    return res;
  }

  private convertDateArrayFromServer(res: HttpResponse<IUserExtendsVM[]>): HttpResponse<IUserExtendsVM[]> {
    return res;
  }
}
