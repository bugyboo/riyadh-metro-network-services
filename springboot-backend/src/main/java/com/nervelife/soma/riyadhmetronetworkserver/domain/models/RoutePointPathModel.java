package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePoint;

public class RoutePointPathModel {

    private Long id;

	private Route route;
	
	private RoutePoint routePoint;
	
	private int pointIndex;

	private Long routeId;
	
	private Long routePointId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public RoutePoint getRoutePoint() {
        return routePoint;
    }

    public void setRoutePoint(RoutePoint routePoint) {
        this.routePoint = routePoint;
    }

    public int getPointIndex() {
        return pointIndex;
    }

    public void setPointIndex(int pointIndex) {
        this.pointIndex = pointIndex;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getRoutePointId() {
        return routePointId;
    }

    public void setRoutePointId(Long routePointId) {
        this.routePointId = routePointId;
    }
    
}
