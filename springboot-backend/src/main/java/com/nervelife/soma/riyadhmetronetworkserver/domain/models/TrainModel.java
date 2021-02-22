package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.TrainTime;

public class TrainModel {

	private Long trainId;
	
	private String trainModelName;
	
	private Route route;
    
    private List<TrainTime> trainTime;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getTrainModel() {
        return trainModelName;
    }

    public void setTrainModel(String trainModelName) {
        this.trainModelName = trainModelName;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<TrainTime> getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(List<TrainTime> trainTime) {
        this.trainTime = trainTime;
    }
    
}
