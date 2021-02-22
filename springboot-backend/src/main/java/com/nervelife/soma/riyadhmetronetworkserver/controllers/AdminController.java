package com.nervelife.soma.riyadhmetronetworkserver.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nervelife.soma.riyadhmetronetworkserver.domain.models.RoutePointModel;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.RoutePointPathModel;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StatusMessage;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePoint;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePointPath;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.RoutePointPathRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.RoutePointRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.RouteRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.StationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/admin")
public class AdminController {

	@Autowired
	StationRepository stationRepo;

	@Autowired
	RouteRepository routeRepo;

	@Autowired
	RoutePointRepository routePointRepo;

	@Autowired
	RoutePointPathRepository pppRepo;

	@GetMapping("/stations")
	public List<Station> getAllStations() {
		return stationRepo.findAll().stream().map( x -> {
			if (x.getRoutePoint() != null) {
				x.getRoutePoint().routesIds = new ArrayList<Long>();
				for (final Route route : x.getRoutePoint().getRoutes()) {
					x.getRoutePoint().routesIds.add(route.getId());
				}
			}
			return x;
		}).collect(Collectors.toList());
	}

	
	@GetMapping("/routes")
	public List<Route> getAllRoutes() {
		return routeRepo.findAll().stream().map(x -> {
			for (final RoutePoint rp : x.getRoutePoints()) {
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

	@GetMapping("/route-points")
	public List<RoutePoint> getAllRoutePoint() {
		return routePointRepo.findAll().stream().map(x -> {
			if (x.getRoutes() != null) {
				final List<Long> rtIds = new ArrayList<>();
				for (final Route rt : x.getRoutes()) {
					rtIds.add(rt.getId());
				}
				x.routesIds = rtIds;
			}
			if (x.getStation() != null) {
				x.stationId = x.getStation().getId();
			}
			return x;
		}).collect(Collectors.toList());
	}

	@PostMapping({ "/route-point/save" })
	public StatusMessage saveRoutePoint(@RequestBody RoutePointModel routePoint) {

		StatusMessage response = new StatusMessage("200", "OK");

		final List<Route> tmpRoutes = new ArrayList<>();
		if (routePoint.getRoutesIds() != null) {
			for (final Long rtId : routePoint.getRoutesIds()) {
				Optional<Route> r = routeRepo.findById(rtId);
				if ( r.isPresent() ){
					tmpRoutes.add( r.get() );
				}
			}
		}

		if (routePoint.getStationId() != null) {
			Optional<Station> tmpStation = stationRepo.findById(routePoint.getStationId());
			if ( tmpStation.isPresent() ){
				routePoint.setStation(tmpStation.get());
			}
		}

		routePoint.setRoutes(tmpRoutes);

		RoutePoint rp = new RoutePoint();
		rp.setId(routePoint.getId());
		rp.setLat(routePoint.getLat());
		rp.setLng(routePoint.getLng());
		rp.setRoutes(routePoint.getRoutes());
		rp.setStation(routePoint.getStation());

		routePointRepo.save(rp);

		return response;
	}

	@PostMapping({ "/ppp/save" })
	public StatusMessage saveRoutePointPath(@RequestBody final RoutePointPathModel pppm) {

		StatusMessage response = new StatusMessage("200", "OK");

		Optional<Route> route = routeRepo.findById(pppm.getRouteId());
		Optional<RoutePoint> routePoint = routePointRepo.findById(pppm.getRoutePointId());

		if (route.isPresent()){
			pppm.setRoute(route.get());
		}
		if (routePoint.isPresent()){
			pppm.setRoutePoint(routePoint.get());
		}

		RoutePointPath ppp = new RoutePointPath();
		ppp.setId(pppm.getId());
		ppp.setPointIndex(pppm.getPointIndex());
		ppp.setRoute(pppm.getRoute());
		ppp.setRoutePoint(pppm.getRoutePoint());

		pppRepo.save(ppp);

		return response;
	}

	@DeleteMapping({ "/station/delete/{stationId}" })
	public StatusMessage deleteStation(@PathVariable final Long stationId) {

		StatusMessage response = new StatusMessage("200", "OK");

		Optional<Station> s = stationRepo.findById(stationId);

		if ( s.isPresent() ){
			stationRepo.delete(s.get());
		}
		
		return response;
	}
    
}
