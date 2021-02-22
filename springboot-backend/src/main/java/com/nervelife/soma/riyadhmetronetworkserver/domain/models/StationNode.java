package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;

public class StationNode {

    	
	private Station station;
	
	@JsonIgnore
	private List<StationNode> shortestPath = new LinkedList<>();
	
	private Long distance = Long.MAX_VALUE;
	
	private Map<StationNode, Long> connectedNodes = new HashMap<>();
	
	public void addDestination(StationNode stationNode, long distance) {
		connectedNodes.put(stationNode, distance);
	}
	
	public StationNode(Station station) {
		this.station = station;
	}

	//======================== GETTERS/SETTERS ========================//	
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public List<StationNode> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<StationNode> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Map<StationNode, Long> getConnectedNodes() {
		return connectedNodes;
	}

	public void setConnectedNodes(Map<StationNode, Long> connectedNodes) {
		this.connectedNodes = connectedNodes;
	}
    
}
