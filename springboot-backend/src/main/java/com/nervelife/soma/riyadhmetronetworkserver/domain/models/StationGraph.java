package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.HashSet;
import java.util.Set;

public class StationGraph {

    private Set<StationNode> stations = new HashSet<>();
	
	public void addStation(StationNode station) {
		this.stations.add(station);
	}
	
	public StationNode findNode(Long id) {
		for (StationNode node : stations) {
			if (node.getStation().getId().equals(id)) {
				return node;
			}
		}
		return null;
	}

	//======================== GETTERS/SETTERS ========================//
	
	public Set<StationNode> getStations() {
		return stations;
	}

	public void setStations(Set<StationNode> stations) {
		this.stations = stations;
	}
    
}
