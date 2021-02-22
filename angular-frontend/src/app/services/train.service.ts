import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiUrl, httpOptions, IStation, ITrain, ITrainTime } from '../models';

@Injectable({
  providedIn: 'root'
})
export class TrainService {

  constructor(private http: HttpClient) { }

  getAllTrains(routeId: number): Observable<ITrain[]> {
    return this.http.get<ITrain[]>(ApiUrl.trains + routeId, httpOptions);
  }

  getAllStations(routeId: number): Observable<IStation[]> {
    return this.http.get<IStation[]>(ApiUrl.trainStations + routeId, httpOptions);
  }

  getTrainTimes(trainId: number, stationId: number): Observable<ITrainTime[]> {
    return this.http.get<ITrainTime[]>(ApiUrl.trainStationTimes + `${trainId}/${stationId}/`);
  }

}
