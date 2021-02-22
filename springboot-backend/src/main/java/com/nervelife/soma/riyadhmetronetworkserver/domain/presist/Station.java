package com.nervelife.soma.riyadhmetronetworkserver.domain.presist;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Station {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String nameArabic;
	
	@Column(length = 5)
	private String stationCode;
	
	@Column(length = 25)
	private String stationType; 	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="routePointId")
	private RoutePoint routePoint;
	
	@JsonIgnore
	@OneToMany(mappedBy="station")
	@OrderBy("hour ASC, minute ASC")
	private List<TrainTime> trainTime;
	
	@Transient
	public double distance;
	
	@Transient
	public List<TrainTime> times = new ArrayList<>();
	
	//======================== GETTERS/SETTERS ========================//

	public Station() {
		super();
	}

	public Station( Station station ) {
		if (station != null) {
			this.id = station.id;
			this.name = station.name;
			this.nameArabic = station.nameArabic;
			this.stationCode = station.stationCode;
			this.stationType = station.stationType;
			this.routePoint = new RoutePoint( station.routePoint );
		}
	}

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

	public RoutePoint getRoutePoint() {
		return routePoint;
	}

	public void setRoutePoint(RoutePoint routePoint) {
		this.routePoint = routePoint;
	}

	public String getStationType() {
		return stationType;
	}

	public void setStationType(String stationType) {
		this.stationType = stationType;
	}

	public List<TrainTime> getTrainTime() {
		return trainTime;
	}

	public void setTrainTime(List<TrainTime> trainTime) {
		this.trainTime = trainTime;
	}
}
