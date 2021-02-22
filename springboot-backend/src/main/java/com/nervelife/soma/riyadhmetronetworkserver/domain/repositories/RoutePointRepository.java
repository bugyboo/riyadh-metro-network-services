package com.nervelife.soma.riyadhmetronetworkserver.domain.repositories;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePoint;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutePointRepository extends CrudRepository<RoutePoint, Long> {
    
    List<RoutePoint> findAll();
    
}
