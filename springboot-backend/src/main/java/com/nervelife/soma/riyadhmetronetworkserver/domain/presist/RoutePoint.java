package com.nervelife.soma.riyadhmetronetworkserver.domain.presist;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RoutePoint {
	
	@Id
	@GeneratedValue
	private Long id;	
	
	private double lat;
	
	private double lng;
	
    @JsonIgnore
    @ManyToMany	
	private List<Route> routes;
	
    @JsonIgnore
    @OneToOne
    @JoinColumn(name="stationId")	
	private Station station;
    
    @Transient
    public List<Long> routesIds;
    
    @Transient
    public Long stationId;
    
	@Transient
	public double distance;
    
  //======================== GETTERS/SETTERS ========================//    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
}
