package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;

public class RoutePointModel {

	private Long id;	
	
	private double lat;
	
	private double lng;
	
	private List<Route> routes;

	private Station station;

    private List<Long> routesIds;

    private Long stationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Long> getRoutesIds() {
        return routesIds;
    }

    public void setRoutesIds(List<Long> routesIds) {
        this.routesIds = routesIds;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }
    
}
