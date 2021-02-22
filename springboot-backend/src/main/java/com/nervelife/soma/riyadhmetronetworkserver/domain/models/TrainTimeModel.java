package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Train;

public class TrainTimeModel {

	private Long timeId;
	
	private String timeType;
	
	private int hour;
	
	private int minute;
	
	private int tripMinutes;
	
	private boolean forward;
	
	private Station station;
	
	private Train train;

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getTripMinutes() {
        return tripMinutes;
    }

    public void setTripMinutes(int tripMinutes) {
        this.tripMinutes = tripMinutes;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
    
}
