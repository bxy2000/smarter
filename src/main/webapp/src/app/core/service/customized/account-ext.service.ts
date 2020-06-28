import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {map} from 'rxjs/operators';
import {SERVER_API_URL} from "../../../app.constants";
import {MenuDTO} from "@core/model/dto/menu-dto.model";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {IUserExtendsDTO} from "@core/model/dto/user-extends-dto.model";

@Injectable({providedIn: 'root'})
export class AccountExtService {
  private resourceUrl = SERVER_API_URL + 'api/ext';

  constructor(private http: HttpClient) {
  }

  getMenu(login: string): Observable<MenuDTO[]> {
    return this.http.get<MenuDTO[]>(this.resourceUrl + '/menu-tree', {
      params: {
        login: `${login}`
      }, observe: 'response'
    }).pipe(map((res: HttpResponse<MenuDTO[]>) => res.body));
  }

  getUserExtends(login: string): Observable<IUserExtendsDTO>{
    return this.http.get<IUserExtendsDTO>(this.resourceUrl+'/user-extends-dto', {
      params: {
        login: `${login}`
      }, observe: 'response'}).pipe(map((res: HttpResponse<IUserExtendsDTO>)=> res.body));
  }
}
