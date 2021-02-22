package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;

public class PathSegment {

    @JsonIgnore
	private Route route;
	private Long routeId;
	
	private List<PathPoint> points;
	
	private long distance;
	
	private Station startStation;
	
	private Station endStation;
	
	@Transient
	public boolean forward;
	
	//======================== GETTERS/SETTERS ========================//	

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public List<PathPoint> getPoints() {
		return points;
	}

	public void setPoints(List<PathPoint> points) {
		this.points = points;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public Station getStartStation() {
		return startStation;
	}

	public void setStartStation(Station startStation) {
		this.startStation = startStation;
	}

	public Station getEndStation() {
		return endStation;
	}

	public void setEndStation(Station endStation) {
		this.endStation = endStation;
	}
    
}
