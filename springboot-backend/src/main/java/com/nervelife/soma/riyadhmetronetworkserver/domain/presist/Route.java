package com.nervelife.soma.riyadhmetronetworkserver.domain.presist;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Route {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 80)
	private String name;
	
	@Column(length = 80)
	private String nameArabic;
	
	@Column(length = 5)
	private String routeCode;
	
	@Column(length = 25)
	private String routeColor;
	
	@JsonIgnore
	@ManyToMany(mappedBy="routes", cascade=CascadeType.ALL)
	private List<RoutePoint> routePoints;
	
	@OneToMany(mappedBy="route", cascade=CascadeType.ALL)
	private List<Train> trains;
	
	@OneToMany(mappedBy="route", cascade=CascadeType.ALL)
	@OrderBy("pointIndex ASC")
	private List<RoutePointPath> routePointsPath;
	
	
	//======================== GETTERS/SETTERS ========================//	

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

	public List<RoutePointPath> getRoutePointsPath() {
		return routePointsPath;
	}

	public void setRoutePointsPath(List<RoutePointPath> routePointsPath) {
		this.routePointsPath = routePointsPath;
	}

	public List<Train> getTrains() {
		return trains;
	}

	public void setTrains(List<Train> trains) {
		this.trains = trains;
	}

}
