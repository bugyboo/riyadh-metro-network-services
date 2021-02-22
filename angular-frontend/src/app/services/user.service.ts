import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { ApiUrl, httpOptions, loginOptions, logoutOptions, StatusMessage, UserSession } from '../models';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  userSession!: UserSession | undefined;

  userNotifications = new Subject();

  constructor(private http: HttpClient) { }

  userInfo(): Observable<StatusMessage> {
    return this.http.get<StatusMessage>(ApiUrl.loginInfo, httpOptions);
  }

  doLogin(username: string, password: string): Observable<StatusMessage> {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    return this.http.post<StatusMessage>(ApiUrl.loginUrl, body, loginOptions );
  }

  doLogout(): Observable<any> {
    return this.http.get<any>(ApiUrl.logoutUrl, logoutOptions);
  }

  getAllUsers(): Observable<UserSession[]> {
    return this.http.get<UserSession[]>( ApiUrl.userAll, httpOptions);
  }

  saveUser(user: UserSession): Observable<StatusMessage> {
    return this.http.post<StatusMessage>(ApiUrl.userSave, user, httpOptions);
  }

  deleteUser(userName: string): Observable<StatusMessage> {
    return this.http.delete<StatusMessage>( ApiUrl.userDelete + `/${userName}`, httpOptions );
  }

}
