package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import java.util.List;

public class PathRoute {

    private List<PathSegment> segments;
	
	private long distance;
	
	//======================== GETTERS/SETTERS ========================//	

	public List<PathSegment> getSegments() {
		return segments;
	}

	public void setSegments(List<PathSegment> segments) {
		this.segments = segments;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}
    
}
