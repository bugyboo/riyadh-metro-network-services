import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IStation } from 'src/app/models';

@Component({
  selector: 'app-station-edit',
  templateUrl: './station-edit.component.html',
  styleUrls: ['./station-edit.component.scss']
})
export class StationEditComponent implements OnInit {

  isUpdate = 'false';

  data: IStation;

  stationTypes = ['underground', 'oboveground' ];

  constructor( public dialogRef: MatDialogRef<StationEditComponent>,
               @Inject(MAT_DIALOG_DATA) public station: IStation) {

      this.data = {};
      if (station != null) {
          this.data = station;
          this.isUpdate = 'true';
      }

  }

  ngOnInit(): void {
  }

}
