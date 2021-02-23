package com.nervelife.soma.riyadhmetronetworkserver.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nervelife.soma.riyadhmetronetworkserver.domain.models.RouteModel;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StationModel;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StatusMessage;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.TrainModel;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.TrainTimeModel;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePoint;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Train;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.TrainTime;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.RoutePointRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.RouteRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.StationRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.TrainRepository;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.TrainTimeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/company")
public class CompanyController {

    @Autowired
    RouteRepository routeRepo;

    @Autowired
    TrainRepository trainRepo;

    @Autowired
    TrainTimeRepository trainTimeRepo;

    @Autowired
    StationRepository stationRepo;

    RoutePointRepository rpRepo;

    @GetMapping("/trains/{routeId}")
    public List<Train> getAllTrains(@PathVariable Long routeId) {

        Optional<Route> route = routeRepo.findById(routeId);

        if (route.isPresent()) {
            return trainRepo.findByRoute(route.get()).stream().map( x -> {
                for (TrainTime tt : x.getTrainTime()) {
                    if ( tt.getStation() != null ) {
                        tt.stationId = tt.getStation().getId();
                    }
                    if ( tt.getTrain() != null ) {
                        tt.trainId = tt.getTrain().getTrainId();
                    }
                }
                return x;
            }).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @GetMapping({"/train-stations/{routeId}"})
	public List<Station> getAllStationsOnRoute(@PathVariable Long routeId) {

        Optional<Route> route = routeRepo.findById(routeId);

        if (route.isPresent()) {
            List<Station> stations = new ArrayList<>();
            for (RoutePoint rp : route.get().getRoutePoints()) {
                if (rp.getStation() != null)
                    stations.add(rp.getStation());
            }
            return stations;
        }

        return Collections.emptyList();

    }

    @GetMapping({"/train-times/{trainId}/{stationId}"})
	public List<TrainTime> getAllTrainsByStation(@PathVariable Long trainId, @PathVariable Long stationId) {

        Optional<Train> train = trainRepo.findById(trainId);
        Optional<Station> station = stationRepo.findById(stationId);

        if (train.isPresent() && station.isPresent()) {

            return trainTimeRepo.findByTrainAndStation(train.get(), station.get()).stream().map(x -> {
                if ( x.getStation() != null ) {
                    x.stationId = x.getStation().getId();
                }
                if ( x.getTrain() != null ) {
                    x.trainId = x.getTrain().getTrainId();
                }
                return x;
            }).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @PostMapping("/trains/{routeId}")
	public StatusMessage saveTrain( @RequestBody TrainModel train, @PathVariable Long routeId ) {

        StatusMessage response = new StatusMessage("200", "OK");
		
		Optional<Route> route = routeRepo.findById(routeId);
		
		if ( train != null && route.isPresent() ) {
            Train t = new Train();
			t.setTrainId(train.getTrainId());
            t.setTrainModel(train.getTrainModel());
            t.setTrainTime(train.getTrainTime());
            t.setRoute(route.get());
			trainRepo.save(t);
		}
		
		return response;
	}

    @DeleteMapping({"/trains/delete/{trainId}"})
	public StatusMessage deleteTrain( @PathVariable Long trainId ) {

        StatusMessage response = new StatusMessage("200", "OK");
		
		Optional<Train> train = trainRepo.findById(trainId);
		
		if ( train.isPresent() ) {
			trainRepo.delete(train.get());
		}
		
		return response;
	}

    @PostMapping({"/train-time/{stationId}/{trainId}"})
	public StatusMessage saveTrainTime( @RequestBody TrainTimeModel trainTime, 
			@PathVariable Long stationId, @PathVariable Long trainId ) {	
                
        StatusMessage response = new StatusMessage("200", "OK");
		
		if ( trainTime != null ) {
			Optional<Station> s = stationRepo.findById(stationId);
			
			if ( s.isPresent() ) {
                TrainTime tt = new TrainTime();
                if (trainTime.getTimeId() != null) {
                    tt.setTimeId(trainTime.getTimeId());
                }
                tt.setTimeType(trainTime.getTimeType());
                tt.setForward(trainTime.isForward());
                tt.setHour(trainTime.getHour());
                tt.setMinute(trainTime.getMinute());
                tt.setStation( s.get() );
				
				Optional<Train> t = trainRepo.findById(trainId);
				if ( t.isPresent() ) {
					tt.setTrain( t.get() );
					trainTimeRepo.save(tt);
				}
			}
		}
		
		return response;
	}

	@DeleteMapping({"/train-time/delete/{trainTimeId}"})
	public StatusMessage deleteTrainTime( @PathVariable Long trainTimeId ) {

        StatusMessage response = new StatusMessage("200", "OK");
		
		Optional<TrainTime> tt = trainTimeRepo.findById( trainTimeId );
		
		if ( tt.isPresent() ) {
			trainTimeRepo.delete( tt.get() );
		}
		
		return response;
	}

    @PostMapping({"/route"})
	public StatusMessage saveRoute( @RequestBody RouteModel route ) {

        StatusMessage response = new StatusMessage("200", "OK");
		
		if ( route != null ) {
			Optional<Route> r = routeRepo.findById( route.getId() );
			if ( r.isPresent() ) {
				r.get().setName( route.getName() );
				r.get().setNameArabic( route.getNameArabic() );
				r.get().setRouteCode( route.getRouteCode() );
				r.get().setRouteColor( route.getRouteColor() );
				
				routeRepo.save( r.get() );
			}
		}
		return response;
	}

	@PostMapping({"/station"})
	public StatusMessage saveStation( @RequestBody StationModel station ) {

        StatusMessage response = new StatusMessage("200", "OK");
		
		if ( station != null ) {
			Optional<Station> s = stationRepo.findById( station.getId() );
			if ( s.isPresent() ) {
				s.get().setName( station.getName() );
				s.get().setNameArabic( station.getNameArabic() );
				s.get().setStationCode( station.getStationCode() );
				s.get().setStationType( station.getStationType() );
				
				stationRepo.save( s.get() );
			}
		}
		return response;
	}
    
}
