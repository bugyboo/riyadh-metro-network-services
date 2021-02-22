import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { IPathResult, IRoute, IStation } from 'src/app/models';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit, AfterViewInit {

  searchForm = new FormGroup({
    destControl: new FormControl(''),
    departureControl: new FormControl('')
  });

  lat = 24.743791268139606;
  lng = 46.66049659252167;
  zoom = 12;

  allRoutes!: IRoute[];
  allStations!: IStation[];

  searchResult: IPathResult = {};

  filteredDestinations: Observable<IStation[]> | undefined;
  filteredDepartues: Observable<IStation[]> | undefined;

  constructor(private searchService: SearchService,
              public snackBar: MatSnackBar) { }

  ngOnInit(): void {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(position => {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;

        this.searchService.getNearestStations(this.lat, this.lng).subscribe(
          (res: IStation) => {
            this.searchForm.get('departureControl')?.setValue( res );
          },
          err => {
            this.snackBar.open(err.error, 'close', { duration: 3000});
          }
        );
      });
   }
  }

  filterStation(name: string): IStation[] {
    return this.allStations.filter( option =>
      option.name?.toLowerCase().indexOf( name.toLowerCase() ) === 0 ||
      option.stationCode?.toLowerCase().indexOf( name.toLowerCase() ) === 0
    );
  }

  ngAfterViewInit(): void {
    this.searchService.getAllRoutes().subscribe(
      (res: IRoute[]) => {
        this.allRoutes = res;
      },
      err => {
        this.snackBar.open(err.error, 'close', { duration: 3000});
      }
    );

    this.searchService.getAllStations().subscribe(
      (res: IStation[]) => {
        this.allStations = res;
        this.filteredDestinations = this.searchForm.get('destControl')?.valueChanges.pipe(
          startWith<string | IStation>(''),
          map( value => typeof value === 'string' ? value : value.name ),
          map( name => name ? this.filterStation(name) : this.allStations.slice() )
        );
        this.filteredDepartues = this.searchForm.get('departureControl')?.valueChanges.pipe(
          startWith<string | IStation>(''),
          map( value => typeof value === 'string' ? value : value.name ),
          map( name => name ? this.filterStation(name) : this.allStations.slice() )
        );
      },
      err => {
        this.snackBar.open(err.error, 'close', { duration: 3000});
      }
    );
  }

  searchPathRoute(): void {
    const destStation = this.searchForm.get('destControl')?.value;
    if ( typeof destStation === 'string' || typeof destStation === 'undefined' ) {
      this.removeDestination();
      this.snackBar.open('Please, select a destination station',
      'Close', { duration: 3000});
      return;
    }
    const departureStation = this.searchForm.get('departureControl')?.value;
    if ( typeof departureStation === 'string' || typeof departureStation === 'undefined') {
      this.removeDeparture();
      this.snackBar.open('Please, select a departure station',
      'Close', { duration: 3000});
      return;
    }
    this.searchService.getRoutePath(departureStation, destStation).subscribe(
      (res: IPathResult) => {
        this.searchResult = res;
        this.fillSearchResult();
      },
      err => {
        this.snackBar.open(err.error, 'close', { duration: 3000});
      }
    );
  }

  fillSearchResult(): void {
    if ( this.searchResult ) {
      this.searchResult.pathRoutes?.forEach( x => {
        x.segments?.forEach( y => {
          let prevTrainTime: any;
          if ( y.startStation?.times && y.startStation?.times?.length > 0 ) {
            if (!y.startStation?.selectedTimeId) {
              y.startStation.selectedTimeId = String(y.startStation.times[0].timeId);
              y.startStation.selectedTime = y.startStation.times[0];
            } else {
              // tslint:disable-next-line: triple-equals
              y.startStation.selectedTime = y.startStation.times.find ( n => n.timeId == y.startStation?.selectedTimeId );
            }
            prevTrainTime = y.startStation?.selectedTime;
          }
          y.points?.forEach( z => {
            const point = z.routePoint;
            if ( point?.stationId ) {
              if ( !point.station ) {
                point.station = this.getStation(point.stationId);
              }
              if (point.stationId === y.startStation?.id) {
                point.station.selectedTime = y.startStation.selectedTime;
              } else {
                const selectedTime = point?.station?.times?.find( n => {
                    if (n.hour) {
                      return n.hour >= prevTrainTime.hour &&
                      n.timeType === 'DEPT' && n.trainId === prevTrainTime.trainId;
                    } else {
                      return false;
                    }
                 }
                );

                if ( selectedTime ) {
                  point.station.selectedTimeId = String(selectedTime.timeId);
                  point.station.selectedTime = selectedTime;
                  prevTrainTime = selectedTime;
                }
              }

            }
          });
        });
      });
    }
  }

  clearSearch(): void {
    this.searchResult = {};

    this.searchForm.patchValue({
      destControl: '',
      departureControl: ''
    });
  }

  onMapClick(event: any): void {
    console.log(event);
  }

  onMarkerClick(event: any): void {
    console.log('Marker = ', event);
  }

  displayStation(station?: IStation): any {
    return station ? station.name : '';
  }

  removeDestination(): void {

    this.searchForm.patchValue({
      destControl: ''
    });
  }

  removeDeparture(): void {

    this.searchForm.patchValue({
      departureControl: ''
    });
  }

  setDestination(st: IStation): void {
    this.searchForm.get('destControl')?.setValue(st);
  }

  setDeparture(st: IStation): void {
    this.searchForm.get('departureControl')?.setValue(st);
  }

  getRouteName(id: number): any {
    const route = this.allRoutes.find( x => x.id === id);
    if (route) {
      return route.name;
    }
    return 'N/A';
  }

  getRouteColor(id: number): any {
    const route = this.allRoutes.find( x => x.id === id);
    if (route) {
      return route.routeColor;
    }
    return 'gray';
  }

  getStation(id: number): IStation {
    const station = this.allStations.find( x => x.id === id);
    if (station) {
      return station;
    }
    return {};
  }

  doSelectChange(station: IStation, obj: any): void {
    this.fillSearchResult();
  }

}
