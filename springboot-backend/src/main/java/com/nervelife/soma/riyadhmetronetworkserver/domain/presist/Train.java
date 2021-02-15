package com.nervelife.soma.riyadhmetronetworkserver.domain.presist;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Train {

	@Id
	@GeneratedValue
	private Long trainId;
	
	@Column(length = 25)
	private String trainModel;
	
    @JsonIgnore
    @ManyToOne		
	private Route route;
    
    @OneToMany(mappedBy="train", cascade=CascadeType.ALL)
    @OrderBy("hour ASC, minute ASC")
    private List<TrainTime> trainTime;
    
	//======================== GETTERS/SETTERS ========================//
    
	public Long getTrainId() {
		return trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}

	public String getTrainModel() {
		return trainModel;
	}

	public void setTrainModel(String trainModel) {
		this.trainModel = trainModel;
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
