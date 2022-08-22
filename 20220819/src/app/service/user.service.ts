import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserModel } from '../model/UserModel';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  options: any;

  mainUrl = environment.userApiUrl;

  constructor(private http: HttpClient) { 
    this.options = {
      headers: new HttpHeaders({
        'content-type': 'application/json',
      }),
    };
  }

  getAllUsers(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(`${this.mainUrl}`);
  }

  getUserById(id: number): Observable<UserModel>  {
    return this.http.get<UserModel>(`${this.mainUrl+'/'+id}`);
  }

  createUser(request: any) {
    return this.http.post(`${this.mainUrl}`, request, this.options);
  }

  updateUser(request: any, id: number) {
    return this.http.put(`${this.mainUrl+'/'+id}`, request, this.options);
  }

  deleteUser(id: number) {
    return this.http.delete(`${this.mainUrl+'/'+id}`);
  }


}
