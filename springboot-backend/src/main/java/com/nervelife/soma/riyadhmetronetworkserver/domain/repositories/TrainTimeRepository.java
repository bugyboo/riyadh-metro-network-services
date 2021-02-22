package com.nervelife.soma.riyadhmetronetworkserver.domain.repositories;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Train;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.TrainTime;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainTimeRepository extends CrudRepository<TrainTime, Long> {

    List<TrainTime> findByTrainAndStation(Train train, Station station);

    List<TrainTime> findByTrainRouteAndStationAndForwardAndTimeType( Route route, Station station, Boolean forward, String timeType );
    
}
