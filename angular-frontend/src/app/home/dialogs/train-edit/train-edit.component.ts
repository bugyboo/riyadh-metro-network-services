import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ITrain } from 'src/app/models';

@Component({
  selector: 'app-train-edit',
  templateUrl: './train-edit.component.html',
  styleUrls: ['./train-edit.component.scss']
})
export class TrainEditComponent implements OnInit {

  isUpdate = 'false';

  data: ITrain;

  constructor( public dialogRef: MatDialogRef<TrainEditComponent>,
               @Inject(MAT_DIALOG_DATA) public train: ITrain) {

      this.data = {};
      if (train != null) {
          this.data = train;
          this.isUpdate = 'true';
      }

  }

  ngOnInit(): void {
  }

}
