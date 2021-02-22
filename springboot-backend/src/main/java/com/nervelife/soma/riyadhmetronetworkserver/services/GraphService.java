package com.nervelife.soma.riyadhmetronetworkserver.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import com.nervelife.soma.riyadhmetronetworkserver.domain.models.ConnectedStation;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StationGraph;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StationNode;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePoint;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePointPath;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.RouteRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.StationRepository;
import com.nervelife.soma.riyadhmetronetworkserver.utils.AppUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraphService {

    private static final Logger log = LoggerFactory.getLogger(GraphService.class);	
	
	@Autowired
	StationRepository stationRepo;
	
	@Autowired
	RouteRepository routeRepo;
	
	private List<ConnectedStation> stationsNetwork = new ArrayList<>();

    @Transactional
	public void initiliazeStationNetwork() {
        
        log.info("----> Initilize Stations Network <----");

        List<Route> routes = routeRepo.findAll();

        for ( Route r : routes ) {
			
			Station previousStation = null;
			RoutePoint previousPoint = null;
			double distance = 0;
			
			Iterator<RoutePointPath> it = r.getRoutePointsPath().iterator();
			while ( it.hasNext() ) {
				RoutePointPath ppp = it.next();	
				if ( previousPoint != null ) {
					
					distance = distance + AppUtils.distance(previousPoint.getLat(), ppp.getRoutePoint().getLat(), 
							previousPoint.getLng(), ppp.getRoutePoint().getLng() );
					
					if ( ppp.getRoutePoint().getStation() != null && previousStation != null ) {
						//Add 2way Connection between station ( previous & current )
						ConnectedStation prevCs = this.createConnectedStation( previousStation );
						ConnectedStation currCs = this.createConnectedStation( ppp.getRoutePoint().getStation() );
						prevCs.getConnections().put( currCs.getStation(), Math.round(distance) );
						currCs.getConnections().put( prevCs.getStation(), Math.round(distance) );
						stationsNetwork.add( currCs );

					}
				}
				previousPoint = ppp.getRoutePoint();				
				if (ppp.getRoutePoint().getStation() != null) {
					distance = 0;
					previousStation = ppp.getRoutePoint().getStation();
					ConnectedStation prevCs = this.createConnectedStation( previousStation );
					stationsNetwork.add( prevCs );
				}
			}
			
		}

    }

    private ConnectedStation createConnectedStation( Station station ) {
		for ( ConnectedStation cs : stationsNetwork ) {
			if (cs.getStation().getId().equals(station.getId())) {
				return cs;
			}
		}
		return new ConnectedStation( station );
	}

    public StationGraph stationsGraphFactory() {
		StationGraph graph = new StationGraph();
		
		for ( ConnectedStation cs : stationsNetwork ) {
			StationNode node = new StationNode( cs.getStation() );
			graph.addStation( node );
		}
		for ( ConnectedStation cs : stationsNetwork ) {
			StationNode sn = graph.findNode( cs.getStation().getId() );
			if ( sn != null ) {
				for ( Entry<Station, Long> entry : cs.getConnections().entrySet() ) {
					sn.addDestination( graph.findNode(entry.getKey().getId()), entry.getValue() );
				}
			}
		}
		
		return graph;		
	}
    
}
