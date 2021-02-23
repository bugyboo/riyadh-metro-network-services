package com.nervelife.soma.riyadhmetronetworkserver.domain.presist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RoutePointPath {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Route route;
	
	@ManyToOne
	private RoutePoint routePoint;
	
	private int pointIndex;
	
	@Transient
	public Long routeId;
	
	@Transient
	public Long routePointId;

    //======================== GETTERS/SETTERS ========================//

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
