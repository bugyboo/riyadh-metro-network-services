import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { IRoute, IStation, ITrain, ITrainTime, StatusMessage } from 'src/app/models';
import { SearchService } from 'src/app/services/search.service';
import { TrainService } from 'src/app/services/train.service';
import { UserService } from 'src/app/services/user.service';
import { DeleteComponent } from '../dialogs/delete/delete.component';
import { RouteEditComponent } from '../dialogs/route-edit/route-edit.component';
import { StationEditComponent } from '../dialogs/station-edit/station-edit.component';
import { TrainEditComponent } from '../dialogs/train-edit/train-edit.component';
import { TrainTimeEditComponent } from '../dialogs/train-time-edit/train-time-edit.component';

@Component({
  selector: 'app-trains',
  templateUrl: './trains.component.html',
  styleUrls: ['./trains.component.scss']
})
export class TrainsComponent implements OnInit, AfterViewInit {

  allRoutes: IRoute[] = [];

  allTrains: ITrain[] = [];

  allStations: IStation[] = [];

  selectedRoute!: number | undefined;

  selectedTrain!: number | undefined;

  selectedStation!: number | undefined;

  dataSource = new MatTableDataSource();

  displayedColumns = ['timeId', 'timeType', 'stationId', 'hour', 'minute',
                      'tripMinutes', 'forward', 'actions'];

  constructor(  private searchService: SearchService,
                private trainService: TrainService,
                private userService: UserService,
                private router: Router,
                public snackBar: MatSnackBar,
                public dialog: MatDialog ) { }

  ngOnInit(): void {
    if (!this.userService.userSession) {
      this.router.navigate(['/']);
    }
  }

  ngAfterViewInit(): void {
    this.loadRoutes();
  }

  loadRoutes(): void {
    this.searchService.getAllRoutes().subscribe(
      (res: IRoute[]) => {
        this.allRoutes = res;
      },
      err => {
        console.log('Routes Error : ', err);
      }
    );
  }

  doRouteSelectChange(): void {
    console.log('selected route = ', this.selectedRoute);
    if (this.selectedRoute !== undefined) {
      this.trainService.getAllTrains(this.selectedRoute).subscribe(
        (res: ITrain[]) => {
          this.allTrains = res;
          this.selectedTrain = undefined;
          this.dataSource.data = [];
        },
        err => {
          console.log('Trains Error : ', err);
        }
      );
      this.trainService.getAllStations(this.selectedRoute).subscribe(
        (res: IStation[]) => {
          this.allStations = res;
          this.selectedStation = undefined;
        },
        err => {
          console.log('Station Error : ', err);
        }
      );
    }
  }

  doTrainSelectChange(): void {
    console.log('selected train = ', this.selectedTrain);
    if ( this.allTrains.length > 0 ){
      const train = this.allTrains.find( x => x.trainId === this.selectedTrain );
      if (train !== undefined) {
        if (train.trainTime !== undefined && train.trainTime.length > 0){
          this.dataSource.data = train.trainTime;
          this.selectedStation = undefined;
        }
      }
    }
  }

  doStationSelectChange(): void {
    console.log('selected station = ', this.selectedStation);
    if ( this.selectedTrain !== undefined && this.selectedStation !== undefined ){
      this.trainService.getTrainTimes(this.selectedTrain, this.selectedStation).subscribe(
        (res: ITrainTime[]) => {
          this.dataSource.data = res;
        },
        err => {
          console.log('TrainTime Error: ', err);
        }
      );
    }
  }

  doEditRoute(routeId: number): void {
    const route = this.allRoutes.find( x => x.id === routeId);
    console.log('Edit Route = ', route);
    const routeDialog = this.dialog.open(RouteEditComponent,
      { width: '500px', data: route });
    routeDialog.afterClosed().subscribe(
      result => {
        console.log('result', result);
        if (result) {
          this.trainService.saveRoute(result).subscribe(
            (res: any) => {
              this.snackBar.open('Route Saved !', 'Close', { duration: 3000});
              this.loadRoutes();
            },
            err => {
              this.snackBar.open('Route NOT Saved !', 'Close', { duration: 3000});
            }
          );
        }
      }
    );
  }

  doEditTrain(trainId: number): void {
    const train = this.allTrains.find( x => x.trainId === trainId );
    console.log('Edit Train = ', train);
    const trainDialog = this.dialog.open(TrainEditComponent,
      { width: '500px', data: train });
    trainDialog.afterClosed().subscribe(
      result => {
        console.log('result', result);
        if (result) {
          if ( this.selectedRoute !== undefined ) {
            this.trainService.saveTrain(this.selectedRoute, result).subscribe(
              (res: any) => {
                this.snackBar.open('Train Saved !', 'Close', { duration: 3000});
                this.doRouteSelectChange();
              },
              err => {
                this.snackBar.open('Train NOT Saved !', 'Close', { duration: 3000});
              }
            );
          }
        }
      }
    );
  }

  doEditStation(stationId: number): void {
    const station = this.allStations.find( x => x.id === stationId );
    console.log('Edit Station = ', station);
    const stationDialog = this.dialog.open(StationEditComponent,
      { width: '500px', data: station });
    stationDialog.afterClosed().subscribe(
      result => {
        console.log('result', result);
        if (result) {
          this.trainService.saveStation(result).subscribe(
            (res: any) => {
              this.snackBar.open('Station Saved !', 'Close', { duration: 3000});
              this.doRouteSelectChange();
            },
            err => {
              this.snackBar.open('Station NOT Saved !', 'Close', { duration: 3000});
            }
          );
        }
      }
    );
  }

  doEditTime(tt: ITrainTime): void {
    console.log('Edit Time = ', tt);
    const trainTimeDialog = this.dialog.open(TrainTimeEditComponent,
      { width: '500px', data: tt });
    trainTimeDialog.afterClosed().subscribe(
      result => {
        console.log('result', result);
        if (result) {
          if ( this.selectedStation !== undefined && this.selectedTrain !== undefined) {
            this.trainService.saveTrainTime(this.selectedStation, this.selectedTrain, result)
              .subscribe(
                (res: any) => {
                  this.snackBar.open('Train Time Saved !', 'Close', { duration: 3000});
                  tt = result;
                },
                err => {
                  this.snackBar.open('Train Time NOT Saved !', 'Close', { duration: 3000});
                }
              );
          }
        }
      }
    );
  }

  doDeleteTime(id: number): void {
    console.log('Delete Time = ', id);
    const deleteDialog = this.dialog.open(DeleteComponent, { width: '500px' });
    deleteDialog.afterClosed().subscribe(
      result => {
        console.log('result', result);
        if (result) {
          this.trainService.deleteTrainTime(id).subscribe(
            (resp: StatusMessage) => {
              console.log('RESP -> ', resp);
              this.snackBar.open('Train Time Delete...', 'Close', { duration: 3000});
              this.doRouteSelectChange();
            },
            err => {
              console.log('err -> ', err);
              this.snackBar.open('Train Time NOT Deleted!', 'Close', { duration: 3000});
            }
          );
        }
      }
    );
  }

  doDeleteTrain(id: number): void {
    if (!id) {
      this.snackBar.open('Please, Select train');
      return;
    }
    console.log('Delete Train = ', id);
    const deleteDialog = this.dialog.open(DeleteComponent, { width: '500px' });
    deleteDialog.afterClosed().subscribe(
      result => {
        console.log('result', result);
        if (result) {
          if (this.selectedTrain !== undefined) {
            this.trainService.deleteTrain(this.selectedTrain).subscribe(
              (res: any) => {
                this.snackBar.open('Train deleted..');
                this.doRouteSelectChange();
              },
              err => {
                this.snackBar.open('Somthing went wrong.. Please, try again later!', 'Close', { duration: 3000});
              });
          }
        }
      }
    );
  }

  doAddTrain(): void {
    if (!this.selectedRoute) {
      this.snackBar.open('Please, select route!', 'Close', { duration: 3000});
      return;
    }
    const train: ITrain = {};
    console.log('Add Train = ', train);
    const trainDialog = this.dialog.open(TrainEditComponent,
      { width: '500px', data: train });
    trainDialog.afterClosed().subscribe(
      result => {
        console.log('result', result);
        if (result) {
          if (this.selectedRoute !== undefined) {
            this.trainService.saveTrain(this.selectedRoute, result).subscribe(
              (res: any) => {
                this.snackBar.open('Train Saved !', 'Close', { duration: 3000});
                this.doRouteSelectChange();
              },
              err => {
                this.snackBar.open('Train NOT Saved !', 'Close', { duration: 3000});
              }
            );
          }
        }
      }
    );
  }

  doAddTime(): void {
    if (!this.selectedTrain || !this.selectedStation) {
      this.snackBar.open('Please, select train and station first !', 'Close', { duration: 3000});
      return;
    }
    const tt: ITrainTime = {};
    const trainTimeDialog = this.dialog.open(TrainTimeEditComponent,
      { width: '500px', data: tt });
    trainTimeDialog.afterClosed().subscribe(
      result => {
        console.log('result', result);
        if (result) {
          if (this.selectedStation !== undefined && this.selectedTrain !== undefined) {
            this.trainService.saveTrainTime(this.selectedStation, this.selectedTrain, result)
              .subscribe(
                (res: any) => {
                  this.snackBar.open('Train Time Saved !', 'Close', { duration: 3000});
                  this.doStationSelectChange();
                },
                err => {
                  this.snackBar.open('Train Time NOT Saved !', 'Close', { duration: 3000});
                }
              );
           }
        }
      }
    );
  }

}
