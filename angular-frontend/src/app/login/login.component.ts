import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiUrl, StatusMessage } from '../models';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username?: string;
  password?: string;

  errorMsg?: string;
  loadingMode = 'determinate';
  isLoading = false;

  constructor(
      private router: Router,
      private userService: UserService ){
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.loadingMode = 'query';
    this.isLoading = true;

    if ( this.username && this.password ) {
      this.userService.doLogin(this.username, this.password)
          .subscribe(
            (resp: StatusMessage) => {
                if (resp.status === '200') {
                  this.userService.userSession = resp.payload;
                  this.router.navigate([ApiUrl.trains]);
                } else {
                  this.loadingMode = 'determinate';
                  this.isLoading = false;
                  this.errorMsg = resp.message;
                  this.userService.userSession = undefined;
                }
                this.userService.userNotifications.next('Info');
              },
              (err: HttpErrorResponse) => {
                this.loadingMode = 'determinate';
                this.isLoading = false;
                if (err.error) {
                    this.errorMsg = err.error.message;
                } else {
                    this.errorMsg = err.message;
                }
              }
          );
      }
  }

  doCancel(): void {
      this.router.navigate(['/']);
  }

}
