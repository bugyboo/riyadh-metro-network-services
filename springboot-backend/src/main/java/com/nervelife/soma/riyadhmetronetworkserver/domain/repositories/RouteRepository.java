package com.nervelife.soma.riyadhmetronetworkserver.domain.repositories;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Route;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
    
    List<Route> findAll();
    
}
