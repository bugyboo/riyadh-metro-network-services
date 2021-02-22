package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePoint;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePointPath;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Train;

public class RouteModel {

	private Long id;
	
	private String name;
	
	private String nameArabic;
	
	private String routeCode;
	
	private String routeColor;

	private List<RoutePoint> routePoints;
	
	private List<Train> trains;
	
	private List<RoutePointPath> routePointsPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameArabic() {
        return nameArabic;
    }

    public void setNameArabic(String nameArabic) {
        this.nameArabic = nameArabic;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePoint> routePoints) {
        this.routePoints = routePoints;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trains) {
        this.trains = trains;
    }

    public List<RoutePointPath> getRoutePointsPath() {
        return routePointsPath;
    }

    public void setRoutePointsPath(List<RoutePointPath> routePointsPath) {
        this.routePointsPath = routePointsPath;
    }
	
}
