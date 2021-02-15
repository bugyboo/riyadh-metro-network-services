package com.nervelife.soma.riyadhmetronetworkserver.domain.presist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TrainTime {
	
	@Id
	@GeneratedValue
	private Long timeId;
	
	@Column(length = 25)
	private String timeType;
	
	private int hour;
	
	private int minute;
	
	private int tripMinutes;
	
	private boolean forward;
	
	@JsonIgnore
	@ManyToOne
	private Station station;
	
	@JsonIgnore
	@ManyToOne
	private Train train;
	
    @Transient
    public Long stationId; 
    
    @Transient
    public Long trainId;
	
	//======================== GETTERS/SETTERS ========================//

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

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    
}
