package com.nervelife.soma.riyadhmetronetworkserver.domain.repositories;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.RoutePointPath;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutePointPathRepository extends CrudRepository<RoutePointPath, Long> {
    
}
