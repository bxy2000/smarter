import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../app.constants';
import { createRequestOption } from '@core/utils/request-util';
import { IMenu } from '@core/model/menu.model';

type EntityResponseType = HttpResponse<IMenu>;
type EntityArrayResponseType = HttpResponse<IMenu[]>;

@Injectable({ providedIn: 'root' })
export class MenuService {
  public resourceUrl = SERVER_API_URL + 'api/menus';

  constructor(protected http: HttpClient) {}

  create(menu: IMenu): Observable<EntityResponseType> {
    return this.http.post<IMenu>(this.resourceUrl, menu, { observe: 'response' });
  }

  update(menu: IMenu): Observable<EntityResponseType> {
    return this.http.put<IMenu>(this.resourceUrl, menu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMenu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMenu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
