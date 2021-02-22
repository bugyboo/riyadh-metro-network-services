package com.nervelife.soma.riyadhmetronetworkserver.domain.repositories;

import java.util.List;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    
    User findByUsername(String username);

    List<User> findAll();
    
}
