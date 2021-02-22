import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserRole, UserSession } from 'src/app/models';

@Component({
  selector: 'app-save-user-dialog',
  templateUrl: './save-user-dialog.component.html',
  styleUrls: ['./save-user-dialog.component.scss']
})
export class SaveUserDialogComponent implements OnInit {

  data: UserSession;

  roles: UserRole[] = [
    { role: 'ADMIN', desc: 'Administrator' },
    { role: 'MANAGER', desc: 'Manager' },
    { role: 'USER', desc: 'User' }
  ];

  isUpdate = 'false';

  constructor(public dialogRef: MatDialogRef<SaveUserDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public user: UserSession) {

    if (user != null) {
      this.data = user;
      this.isUpdate = 'true';
    } else {
      this.data = {};
    }
  }

  ngOnInit(): void {
  }

}
