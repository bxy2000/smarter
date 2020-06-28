import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';
import { SERVER_API_URL } from '../../../app.constants';
import { HttpResponse, HttpErrorResponse} from '@angular/common/http';
import {ResponseMessage} from "@core/model/dto/response-message-dto.model";
import {map} from "rxjs/operators";



@Injectable()
export class PasswordExtService {
    flag: boolean;

    constructor(private http: HttpClient) {}

    save(params: any): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/account/change-password', params, { observe: 'response' });
    }

    compare(password: string, success: any, error: any): void{
      this.comparePWD(password).subscribe((res: HttpResponse<ResponseMessage>) => {
        if(res.body.success){
            success();
        }else{
            error();
        }
      });
    }
    comparePWD(password: string): Observable<HttpResponse<ResponseMessage>> {
      return this.http.post<ResponseMessage>(SERVER_API_URL + 'api/ext/account/compare-password', password, { observe: 'response' })
        .pipe(map((res: HttpResponse<ResponseMessage>) => this.convertResponse(res)));
    }

  private convertResponse(res: HttpResponse<ResponseMessage>): HttpResponse<ResponseMessage> {
    const body: ResponseMessage = this.convertItemFromServer(res.body);
    return res.clone({body});
  }

  private convertItemFromServer(responseMessage: ResponseMessage): ResponseMessage {
    const copy: ResponseMessage = {...responseMessage};
    return copy;
  }
}
