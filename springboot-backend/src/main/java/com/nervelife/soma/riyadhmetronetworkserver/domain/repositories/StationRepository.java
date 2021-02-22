package com.nervelife.soma.riyadhmetronetworkserver.domain.repositories;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.Station;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends CrudRepository<Station, Long> {
    
    List<Station> findAll();
    
}
