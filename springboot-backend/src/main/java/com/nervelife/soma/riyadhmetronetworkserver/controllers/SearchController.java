package com.nervelife.soma.riyadhmetronetworkserver.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nervelife.soma.riyadhmetronetworkserver.domain.models.PathPoint;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.PathResult;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.PathRoute;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.PathSegment;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StationGraph;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StationNode;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StatusMessage;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePoint;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePointPath;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.TrainTime;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.User;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.RoutePointPathRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.RoutePointRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.RouteRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.StationRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.TrainTimeRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.UserRepository;
import com.nervelife.soma.riyadhmetronetworkserver.services.GraphService;
import com.nervelife.soma.riyadhmetronetworkserver.utils.AppUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class SearchController {

    @Autowired
	StationRepository stationRepo;
	
	@Autowired
	RouteRepository routeRepo;
	
	@Autowired
	RoutePointRepository routePointRepo;
	
	@Autowired
	RoutePointPathRepository pppRepo;	
	
	@Autowired
	TrainTimeRepository trainTimeRepo;
	
	@Autowired
	GraphService graphService;

    @Autowired
	UserRepository userRepo;

    @GetMapping("/userinfo")
    public StatusMessage userInfo(Authentication auth) {

        StatusMessage response = new StatusMessage(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "OK");

        if (auth !=  null) {
            org.springframework.security.core.userdetails.User usr = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
            Optional<User> user = userRepo.findById( usr.getUsername() );
            if (user.isPresent()) {
                response.setStatus(String.valueOf(HttpStatus.OK.value()));
                response.setMessage("OK");
                response.setPayload( user.get() );
                return response;
            }
        }

        return response;
    }

    @GetMapping("/search/routes")
	public List<Route> getAllRoutes() {
		return routeRepo.findAll().stream().map(x -> {
			for (RoutePoint rp : x.getRoutePoints()) {
				if (rp.routesIds == null) {
					rp.routesIds = new ArrayList<Long>();
				}
				rp.routesIds.add(x.getId());
				if (rp.getStation() != null) {
					rp.stationId = rp.getStation().getId();
				}
			}
			return x;
		}).collect(Collectors.toList());
	}

    @GetMapping("/search/stations")
	public List<Station> getAllStations() {

		return stationRepo.findAll().stream().map(x -> {
			if ( x.getRoutePoint() != null ) {
				x.getRoutePoint().routesIds = new ArrayList<Long>();
				for (Route route : x.getRoutePoint().getRoutes() ) {
					x.getRoutePoint().routesIds.add( route.getId() );
				}
			}
			for ( TrainTime tt : x.getTrainTime() ) {
				tt.trainId = tt.getTrain().getTrainId();				
			}
			x.times = x.getTrainTime();
			return x;
		}).collect(Collectors.toList());
	}

    @GetMapping("/search/nearest/{lat}/{lng}/p")
	public Station findNearest( @PathVariable double lat, @PathVariable double lng ) {
		
		List<Station> stations = stationRepo.findAll().stream().map(x -> {
			x.distance = AppUtils.distance(x.getRoutePoint().getLat(), lat, x.getRoutePoint().getLng(), lng);
			return x;
		}).sorted( (a,b) -> Double.compare(a.distance, b.distance) ).collect(Collectors.toList());
		
		if ( stations != null && !stations.isEmpty() ) {
			return stations.get(0);
		}
		return null;
	}

    @GetMapping({"/search/graph/{pickupId}/{distId}"})
	public List<StationNode> searchGraph(@PathVariable Long pickupId, @PathVariable Long distId) {

		StationGraph graph = this.graphService.stationsGraphFactory();
		graph = AppUtils.calculateShortestPathFromSource(graph, graph.findNode(pickupId) );
		
		StationNode distNode = graph.findNode(distId);
		distNode.getShortestPath().add(distNode);
		
		return distNode.getShortestPath();
	}

    
	@GetMapping({"/search/path/{pickupId}/{distId}"})
	public PathResult searchForPath( @PathVariable Long pickupId, @PathVariable Long distId ) {
		
		Optional<Station> pickupSt = stationRepo.findById(pickupId);
		Optional<Station> distSt = stationRepo.findById(distId);
		PathResult pathResult = new PathResult();

        if (!pickupSt.isPresent() || !distSt.isPresent()) {
            return pathResult;
        }

		pathResult.setDestination(distSt.get());
		pathResult.setPickup(pickupSt.get());
		pathResult.setPathRoutes( new ArrayList<>() );
			
        Route sameRoute = getSameRoutePath(distSt.get(), pickupSt.get());
        if (sameRoute != null) {
            PathRoute pr = new PathRoute();
            PathSegment ps = this.getPathSegment(sameRoute, pickupSt.get().getRoutePoint(), distSt.get().getRoutePoint());
            if ( ps != null ) {
                List<PathSegment> pss = new ArrayList<>();
                pss.add(ps);
                pr.setDistance(ps.getDistance());
                pr.setSegments(pss);
                pathResult.getPathRoutes().add(pr);
                pathResult.setDistance(pr.getDistance());
            }
        } else {
            //Get Shortest PathRoutes List
            StationGraph graph = this.graphService.stationsGraphFactory();
            graph = AppUtils.calculateShortestPathFromSource(graph, graph.findNode(pickupId) );
            
            StationNode distNode = graph.findNode(distId);
            distNode.getShortestPath().add(distNode);
            
            PathRoute pr = new PathRoute();
            Station startStation = null;
            Station previousStation = null;
            Route previousSameRoute = null;
            List<PathSegment> pss = new ArrayList<>();
            
            for ( StationNode sn : distNode.getShortestPath() ) {
                if ( previousStation == null ) {
                    previousStation = sn.getStation();
                    startStation = previousStation;
                    continue;
                }
                Optional<Station> currentStation = stationRepo.findById( sn.getStation().getId() );
                if (currentStation.isPresent()) {
					Optional<Station> tempSt = stationRepo.findById( previousStation.getId() );
					if (tempSt.isPresent() && previousSameRoute == null) {
						previousStation = tempSt.get();
						previousSameRoute = this.getSameRoutePath( previousStation, currentStation.get() );
					}				

                    sameRoute =  this.getSameRoutePath( previousStation, currentStation.get() );
                    if ( previousSameRoute == sameRoute ) {
                        previousStation = currentStation.get();
                        continue;
                    }

                    PathSegment ps = this.getPathSegment(previousSameRoute, startStation.getRoutePoint(), previousStation.getRoutePoint());
                    pss.add( ps );
                    pr.setDistance( pr.getDistance() + ps.getDistance() );
                    previousSameRoute = sameRoute;
                    startStation = previousStation;
                    previousStation = currentStation.get();
                }
            }
            PathSegment ps = this.getPathSegment(previousSameRoute, startStation.getRoutePoint(), previousStation.getRoutePoint());
            pss.add( ps );
            pr.setDistance( pr.getDistance() + ps.getDistance() );
            pr.setSegments( pss );
            pathResult.getPathRoutes().add(pr);
            pathResult.setDistance(pr.getDistance());
        }
			
		return pathResult;
	}

    private Route getSameRoutePath( Station targetSt, Station sourceSt ) {
		Iterator<Route> routeIt = targetSt.getRoutePoint().getRoutes().iterator();
		// Check if target station is on same route as source station.
		while (routeIt.hasNext()) {
			Route r = routeIt.next();
			Iterator<Route> sourceRouteIt = sourceSt.getRoutePoint().getRoutes().iterator();
			while (sourceRouteIt.hasNext()) {
				Route sr = sourceRouteIt.next();
				if (r.getId().equals(sr.getId())) {
					return r;
				}
			}
		}
		return null;
	}

    private PathSegment getPathSegment( Route route, RoutePoint startRp, RoutePoint endRp ) {

        PathSegment ps = new PathSegment();

        List<PathPoint> pps = new ArrayList<>();

        Map<Integer, RoutePoint> points = new HashMap<>();

        int psIdx = 0;
		int startIdx = 0;
		int endIdx = 0;

		ps.setDistance(0);
		ps.setPoints( pps );
		ps.setRouteId( route.getId() );
		ps.setStartStation( new Station( startRp.getStation() ));
		ps.setEndStation( new Station( endRp.getStation() ));
		
		
		for (RoutePointPath ppp : route.getRoutePointsPath()) {
			points.put(ppp.getPointIndex(), ppp.getRoutePoint());
			if (ppp.getRoutePoint().getId().equals( startRp.getId() ) ) {
				startIdx = ppp.getPointIndex();
			}
			if (ppp.getRoutePoint().getId().equals( endRp.getId() ) ) {
				endIdx = ppp.getPointIndex();
			}
			ppp.getRoutePoint().routesIds = new ArrayList<Long>();
			for ( Route r : ppp.getRoutePoint().getRoutes() ) {
				ppp.getRoutePoint().routesIds.add(r.getId());
			}
			if ( ppp.getRoutePoint().getStation() != null ) {
				ppp.getRoutePoint().stationId = ppp.getRoutePoint().getStation().getId();
			}
		}
		
		RoutePoint prevPoint = startRp;
		
		if (startIdx < endIdx) {
			ps.forward = true;
			for ( int i = startIdx; i <= endIdx; i++ ) {
				PathPoint pp = new PathPoint();
				pp.setIndex(psIdx);
				pp.setRoutePoint(points.get(i));
				pps.add(pp);
				psIdx++;
				long distance = Math.round( AppUtils.distance(prevPoint.getLat(), points.get(i).getLat(), 
						prevPoint.getLng(), points.get(i).getLng()));
				ps.setDistance( ps.getDistance() + distance );
				prevPoint = points.get(i);
				prevPoint.distance = distance;
			}
			
		} else {
			ps.forward = false;
			for ( int i = startIdx; i >= endIdx; i--) {
				PathPoint pp = new PathPoint();
				pp.setIndex(psIdx);
				pp.setRoutePoint(points.get(i));
				pps.add(pp);
				psIdx++;
				long distance = Math.round( AppUtils.distance(prevPoint.getLat(), points.get(i).getLat(), 
						prevPoint.getLng(), points.get(i).getLng()));
				ps.setDistance( ps.getDistance() + distance );
				prevPoint = points.get(i);
				prevPoint.distance = distance;			
			}
		}

        ps.getStartStation().times = trainTimeRepo.findByTrainRouteAndStationAndForwardAndTimeType(route, ps.getStartStation(), ps.forward, "DEPT")
                                                    .stream().map( x -> {
                                                        x.trainId = x.getTrain().getTrainId();
                                                        return x;
                                                    }).sorted( (a,b) -> Integer.compare(a.getHour() , b.getHour()) ).collect(Collectors.toList());
		
		if (ps.getPoints().isEmpty()) {
			return null;
		}
		
		return ps;
	}
    
}
