import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IRoute } from 'src/app/models';

@Component({
  selector: 'app-route-edit',
  templateUrl: './route-edit.component.html',
  styleUrls: ['./route-edit.component.scss']
})
export class RouteEditComponent implements OnInit {

  isUpdate = 'false';

  data: IRoute;

  routeColors = ['Blue', 'Red', 'Green', 'Yellow', 'White', 'Purple', 'Orange',
                 'Cyan', 'Black', 'Pink'];

  constructor( public dialogRef: MatDialogRef<RouteEditComponent>,
               @Inject(MAT_DIALOG_DATA) public route: IRoute) {

      this.data = {};
      if (route != null) {
          this.data = route;
          this.isUpdate = 'true';
      }

  }

  ngOnInit(): void {
  }

}
