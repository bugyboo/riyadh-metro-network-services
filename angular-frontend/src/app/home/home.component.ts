import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserSession } from '../models';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  user!: UserSession | undefined;

  userNotifications!: Subscription;

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit(): void {

    this.user = this.userService.userSession;

    this.userNotifications = this.userService.userNotifications.subscribe(
      (data: any) => {
        this.user = this.userService.userSession;
      }
    );

  }

  ngOnDestroy(): void {
    this.userNotifications.unsubscribe();
  }

  userHasRoleAdmin(): boolean {
    return (this.user !== undefined && this.user.role === 'ADMIN');
  }

  doLogout(): void {
    this.userService.doLogout().subscribe(
      () => {
        this.user = undefined;
        this.userService.userSession = undefined;
        this.userService.userNotifications.next('info');
        this.router.navigate(['']);
      },
      err => {
        this.user = undefined;
        this.userService.userSession = undefined;
        this.userService.userNotifications.next('info');
        this.router.navigate(['']);
      });
  }

}
