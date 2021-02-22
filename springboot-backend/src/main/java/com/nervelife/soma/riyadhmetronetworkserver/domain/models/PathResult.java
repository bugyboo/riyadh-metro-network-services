package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;

public class PathResult {

    private Station destination;
	
	private Station pickup;
	
	private long distance;
	
	private List<PathRoute> pathRoutes;
	
	//======================== GETTERS/SETTERS ========================//	

	public Station getDestination() {
		return destination;
	}

	public void setDestination(Station destination) {
		this.destination = destination;
	}

	public Station getPickup() {
		return pickup;
	}

	public void setPickup(Station pickup) {
		this.pickup = pickup;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public List<PathRoute> getPathRoutes() {
		return pathRoutes;
	}

	public void setPathRoutes(List<PathRoute> pathRoutes) {
		this.pathRoutes = pathRoutes;
	}
    
}
