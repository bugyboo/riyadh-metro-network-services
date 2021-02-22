package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePoint;

public class PathPoint {

    private int index;
	
	private RoutePoint routePoint;
	
	//======================== GETTERS/SETTERS ========================//		

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public RoutePoint getRoutePoint() {
		return routePoint;
	}

	public void setRoutePoint(RoutePoint routePoint) {
		this.routePoint = routePoint;
	}
    
}
