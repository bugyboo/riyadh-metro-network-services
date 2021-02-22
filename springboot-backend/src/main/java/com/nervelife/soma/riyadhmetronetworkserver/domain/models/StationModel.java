package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePoint;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.TrainTime;

public class StationModel {

	private Long id;
	
	private String name;
	
	private String nameArabic;
	
	private String stationCode;
	
	private String stationType; 	
	
	private RoutePoint routePoint;
	
	private List<TrainTime> trainTime;

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

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public RoutePoint getRoutePoint() {
        return routePoint;
    }

    public void setRoutePoint(RoutePoint routePoint) {
        this.routePoint = routePoint;
    }

    public List<TrainTime> getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(List<TrainTime> trainTime) {
        this.trainTime = trainTime;
    }
    
}
