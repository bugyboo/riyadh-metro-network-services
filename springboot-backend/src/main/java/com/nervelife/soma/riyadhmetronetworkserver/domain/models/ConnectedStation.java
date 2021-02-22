package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.HashMap;
import java.util.Map;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;

public class ConnectedStation {
	
	private Station station;
	
	private Map<Station, Long> connections = new HashMap<>();
	
	public ConnectedStation( Station station ) {
		this.station = station;
	}
	
	//======================== GETTERS/SETTERS ========================//	

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Map<Station, Long> getConnections() {
		return connections;
	}

	public void setConnections(Map<Station, Long> connections) {
		this.connections = connections;
	}

}
