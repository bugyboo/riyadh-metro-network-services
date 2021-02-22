package com.nervelife.soma.riyadhmetronetworkserver.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StationGraph;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StationNode;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppUtils {

    private AppUtils(){}
    	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AppUtils.class);	
	
	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * 
	 * Reference - https://en.wikipedia.org/wiki/Haversine_formula
	 * Code Reference - http://rosettacode.org/wiki/Haversine_formula
	 * 
	 * @returns Distance in Meters
	 */
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters
	    double height = 0;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
	/**
	 * 
	 * Dijkstra algorithm
	 * Finding the shortest path between two nodes.
	 * 
	 * Reference - https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
	 * Code Reference - http://www.baeldung.com/java-dijkstra 
	 * 
	 * @param graph
	 * @param source
	 * @return StationsGraph
	 */
	public static StationGraph calculateShortestPathFromSource(StationGraph graph, StationNode source) {
		
		source.setDistance(0L);
		
		Set<StationNode> settledNodes = new HashSet<>();
		Set<StationNode> unsettledNodes = new HashSet<>();
		
		unsettledNodes.add(source);
		StationNode currentNode = null;
		
		while (unsettledNodes.size() != 0) {
			 currentNode = getLowestDistanceNode( unsettledNodes );
			 unsettledNodes.remove(currentNode);
			 
			 for (Entry<StationNode, Long> connectedPair: currentNode.getConnectedNodes().entrySet()) {
				 StationNode connectedNode = connectedPair.getKey();
				 Long edgeWeight = connectedPair.getValue();
				 if (!settledNodes.contains(connectedNode)) {
					 calculateMinimumDistance(connectedNode, edgeWeight, currentNode);
					 unsettledNodes.add(connectedNode);
				 }
			 }
			 settledNodes.add(currentNode);
		}
		
		return graph;
	}
	
	private static StationNode getLowestDistanceNode(Set<StationNode> unsettledNodes) {
		StationNode lowestDistanceNode = null;
		long lowestDistance = Long.MAX_VALUE;
		
		for (StationNode node : unsettledNodes) {
			long nodeDistance = node.getDistance();
	        if (nodeDistance < lowestDistance) {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }			
		}
		
		return lowestDistanceNode;
	}
	
	private static void calculateMinimumDistance(StationNode evaluationNode, Long edgeWeight, StationNode sourceNode) {
		
		Long sourceDistance = sourceNode.getDistance();
		if ( sourceDistance + edgeWeight < evaluationNode.getDistance() ) {
			evaluationNode.setDistance(sourceDistance + edgeWeight);
			LinkedList<StationNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}
	
	public static StationNode findNode(StationGraph graph, Long stationId) {

		Iterator<StationNode> it = graph.getStations().iterator();
		while ( it.hasNext() ) {
			StationNode node = it.next();
			if (node.getStation().getId().equals(stationId)) {
				return node;
			}
		}
		
		return null;
	}

}
