import { Component, OnInit } from '@angular/core';
import { StatusMessage } from './models';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'riyadh-metro-network-client';

  constructor( private userService: UserService ) {}

  ngOnInit(): void {
    this.userService.userInfo().subscribe(
      (resp: StatusMessage) => {
        console.log('userinfo -> ', resp);
        if (resp.status === '200') {
          this.userService.userSession = resp.payload;
        } else {
          this.userService.userSession = undefined;
        }
        console.log('App N-> ', resp.payload);
        this.userService.userNotifications.next('info');
      },
      err => {
        console.log('Error', err);
      }
    );
  }

}
