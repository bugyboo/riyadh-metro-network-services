import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StatusMessage, UserSession } from 'src/app/models';
import { UserService } from 'src/app/services/user.service';
import { DeleteUserDialogComponent } from '../dialogs/delete-user-dialog/delete-user-dialog.component';
import { SaveUserDialogComponent } from '../dialogs/save-user-dialog/save-user-dialog.component';

@Component({
  selector: 'app-user-manager',
  templateUrl: './user-manager.component.html',
  styleUrls: ['./user-manager.component.scss']
})
export class UserManagerComponent implements OnInit, AfterViewInit {

  users: UserSession[] | undefined;

  isLoading = true;

  constructor(
    private userService: UserService,
    public snackBar: MatSnackBar,
    public dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.isLoading = true;
    this.userService.getAllUsers().subscribe(
      (data: UserSession[]) => {
        this.isLoading = false;
        this.users = data;
      },
      err => {
        this.isLoading = false;
        this.snackBar.open('Error retreving data...', 'close', { duration: 3000 });
      }
    );
  }

  openUserDialog(): void {
    const userDialog = this.dialog.open(SaveUserDialogComponent, {
      width: '600px'
    });

    userDialog.afterClosed().subscribe(
      result => {
        if (result) {
          this.saveUser(result);
        }
      }
    );
  }

  updateUserDialog(user: UserSession): void {
    const userDialog = this.dialog.open(SaveUserDialogComponent, {
      width: '600px',
      data: user
    });

    userDialog.afterClosed().subscribe(
      result => {
        if (result) {
          this.saveUser(result);
        }
      }
    );
  }

  deleteUserDialog(username: string): void {
    const deleteDialog = this.dialog.open(DeleteUserDialogComponent, {
      width: '600px'
    });

    deleteDialog.afterClosed().subscribe(
      result => {
        if (result) {
          this.isLoading = true;
          this.userService.deleteUser(username).subscribe(
            (resp: StatusMessage) => {
              this.ngAfterViewInit();
              this.snackBar.open('User Deleted..', 'Close', { duration: 3000 });
            },
            err => {
              this.isLoading = false;
              this.snackBar.open('Error in deleting user!', 'Close', { duration: 3000 });
            }
          );
        }
      }
    );
  }

  saveUser(data: UserSession): void {
    if (data) {
      this.isLoading = true;
      this.userService.saveUser(data).subscribe(
        (resp: StatusMessage) => {
          console.log(resp);
          this.ngAfterViewInit();
          this.snackBar.open('User saved!', 'Close', { duration: 3000 });
        },
        err => {
          this.isLoading = false;
          this.snackBar.open('Error in saving user data!', 'Close', { duration: 3000 });
        }
      );
    }
  }

}
