package com.nervelife.soma.riyadhmetronetworkserver.components;

import java.util.Optional;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.User;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.UserRepository;
import com.nervelife.soma.riyadhmetronetworkserver.services.GraphService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupComponent {

    private static final Logger log = LoggerFactory.getLogger(StartupComponent.class);

    @Autowired
	GraphService graphService;

    @Autowired
	UserRepository usrRepo;

    @EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {

        log.info("~~~___(((( Welcome To NerveLife RMN Project ))))___~~~");

        Optional<User> admin = usrRepo.findById("admin");
		
		if (!admin.isPresent()) {
			User adminUser = new User();
			adminUser.setUsername("admin");
			adminUser.setPassword("admin123");
			adminUser.setRole("ADMIN");
			adminUser.setFullName("Admin Account");
			
			usrRepo.save(adminUser);
			log.info(">>> ADMIN ACCOUNT CREATED...");
		}

        this.graphService.initiliazeStationNetwork();

    }
    
}
