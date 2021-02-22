
import { HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

export const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}), withCredentials: true
};

export const loginOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded', 'Return-Type': 'application/json'}), withCredentials: true
};

export const logoutOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json', responseType: 'text'}), withCredentials: true
};

export interface StatusMessage {
  status: string;
  message: string;
  payload: object;
}

export interface UserRole {
  role: string;
  desc: string;
}

export interface IRoutePoint {
  id?: number;
  lat?: number;
  lng?: number;
  routesIds?: number[];
  stationId?: number;
  station?: IStation;
}

export interface IPathPoint {
  index?: number;
  routePoint?: IRoutePoint;
}

export interface IPathSegment {
  routeId?: number;
  points?: IPathPoint[];
  distance?: number;
  startStation?: IStation;
  endStation?: IStation;
  totalTime?: number;
}

export interface IPathRoute {
  segments?: IPathSegment[];
  distance?: number;
  totalTime?: number;
}

export interface IPathResult {
  destination?: IStation;
  pickup?: IStation;
  distance?: number;
  pathRoutes?: IPathRoute[];
}

export interface IStation {
  id?: number;
  name?: string;
  nameArabic?: string;
  stationCode?: string;
  stationType?: string;
  routePoint?: IRoutePoint;
  distance?: number;
  times?: ITrainTime[];
  selectedTimeId?: string;
  selectedTime?: ITrainTime;
}

export interface IRoute {
  id?: number;
  name?: string;
  nameArabic?: string;
  routeCode?: string;
  routeColor?: string;
  routePoints?: IRoutePoint[];
  trains?: ITrain[];
}

export interface ITrain {
  trainId?: number;
  trainModel?: string;
  trainTime?: ITrainTime[];
}

export interface ITrainTime {
  timeId?: number;
  timeType?: string;
  hour?: number;
  minute?: number;
  tripMinutes?: number;
  forward?: boolean;
  stationId?: number;
  trainId?: number;
}

export interface UserSession {
  username?: string;
  role?: string;
  fullName?: string;
  password?: string;
  gender?: string;
}

export const ApiUrl = {
  loginUrl:  environment.apiRoot + '/login',
  logoutUrl: environment.apiRoot + '/logout',
  loginInfo: environment.apiRoot + '/api/public/userinfo',
  searchAllRoutes: environment.apiRoot + '/api/public/search/routes',
  searchAllStations: environment.apiRoot + '/api/public/search/stations',
  searchForPath: environment.apiRoot + '/api/public/search/path/',
  nearestStations: environment.apiRoot + '/api/public/search/nearest/',
  trains: environment.apiRoot + '/api/private/company/trains/',
  trainStations: environment.apiRoot + '/api/private/company/train-stations/',
  trainStationTimes: environment.apiRoot + '/api/private/company/train-times/',
  trainTime: environment.apiRoot + '/api/private/company/train-time',
  route: environment.apiRoot + '/api/private/company/route',
  station: environment.apiRoot + '/api/private/company/station',
  delete: environment.apiRoot + '/delete',
  userAll: environment.apiRoot + '/api/private/user/all',
  userSave: environment.apiRoot + '/api/private/user/save',
  userDelete: environment.apiRoot + '/api/private/user/delete'
};

export class Models {
}
