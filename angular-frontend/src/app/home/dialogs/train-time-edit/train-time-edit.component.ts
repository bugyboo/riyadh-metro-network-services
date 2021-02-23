import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ITrainTime } from 'src/app/models';

@Component({
  selector: 'app-train-time-edit',
  templateUrl: './train-time-edit.component.html',
  styleUrls: ['./train-time-edit.component.scss']
})
export class TrainTimeEditComponent implements OnInit {

  isUpdate = 'false';

  data: ITrainTime;

  timeTypes = ['DEPT', 'WAIT'];

  directions = ['true', 'false'];

  hours = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
           18, 19, 20, 21, 22, 23, 24];

  constructor( public dialogRef: MatDialogRef<TrainTimeEditComponent>,
               @Inject(MAT_DIALOG_DATA) public trainTime: ITrainTime) {

      this.data = {};
      if (trainTime != null) {
          this.data = trainTime;
          this.isUpdate = 'true';
      }

  }

  ngOnInit(): void {
  }

}
