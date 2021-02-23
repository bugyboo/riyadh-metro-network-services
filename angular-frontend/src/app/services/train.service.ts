import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiUrl, httpOptions, IRoute, IStation, ITrain, ITrainTime, StatusMessage } from '../models';

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

  saveTrain(routeId: number, train: ITrain): Observable<StatusMessage> {
      return this.http.post<StatusMessage>( ApiUrl.trains +
              `${routeId}`, train, httpOptions);
  }

  deleteTrain(trainId: number): Observable<StatusMessage> {
      return this.http.delete<StatusMessage>( ApiUrl.trains +
              `delete/${trainId}`, httpOptions );
  }

  saveTrainTime(stationId: number, trainId: number, trainTime: ITrainTime): Observable<StatusMessage> {
      return this.http.post<StatusMessage>( ApiUrl.trainTime +
              `/${stationId}/${trainId}`, trainTime, httpOptions );
  }

  deleteTrainTime(trainTimeId: number): Observable<StatusMessage> {
      return this.http.delete<StatusMessage>( ApiUrl.trainTime +
              `/delete/${trainTimeId}`, httpOptions );
  }

  saveRoute(route: IRoute): Observable<StatusMessage> {
      return this.http.post<StatusMessage>( ApiUrl.route, route, httpOptions );
  }

  saveStation(station: IStation): Observable<StatusMessage> {
      return this.http.post<StatusMessage>( ApiUrl.station, station, httpOptions);
  }

}
