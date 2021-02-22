import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiUrl, httpOptions, IPathResult, IRoute, IStation } from '../models';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  getAllRoutes(): Observable<IRoute[]> {
    return this.http.get<IRoute[]>(ApiUrl.searchAllRoutes, httpOptions);
  }

  getAllStations(): Observable<IStation[]> {
    return this.http.get<IStation[]>(ApiUrl.searchAllStations, httpOptions);
  }

  getRoutePath(departure: IStation, dest: IStation): Observable<IPathResult> {
    return this.http.get<IPathResult>(ApiUrl.searchForPath + `${departure.id}/${dest.id}`, httpOptions);
  }

  getNearestStations(lat: number, lng: number): Observable<IStation> {
    return this.http.get<IStation>(ApiUrl.nearestStations + `${lat}/${lng}/p`, httpOptions);
  }

}
