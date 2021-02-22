package com.nervelife.soma.riyadhmetronetworkserver.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StatusMessage;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.UserModel;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.User;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
	UserRepository userRepo;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping({ "/all" })
	public List<User> findAll() {

		return userRepo.findAll();
	}

    @PostMapping("/save")
    public StatusMessage save(@RequestBody @Valid UserModel user, Authentication auth){

        log.info("Save User: {} - {}", user.getFullName(), user.getUsername());
        
        StatusMessage response = new StatusMessage("200", "OK");

        org.springframework.security.core.userdetails.User usr = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        Boolean isAdmin = false;
        for (GrantedAuthority ga : usr.getAuthorities()){
            if (ga.getAuthority().equals("ROLE_ADMIN")){
                isAdmin = true;
                break;
            }
        }

        if (Boolean.FALSE.equals(isAdmin) && !usr.getUsername().equals(user.getUsername())) {
			response.setStatus(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			response.setMessage("Make sure you're logged in..");
			return response;
		}

        User u = new User();
        u.setUsername(user.getUsername().trim());
        u.setFullName(user.getFullName());
        u.setGender(user.getGender());
        u.setRole(user.getRole());

        if (user.getPassword() != null && user.getPassword().length() > 0) {
            u.setPassword(user.getPassword());
        }

        userRepo.save(u);

        return response;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{username}")
    public StatusMessage delete(@PathVariable String username, Authentication auth){

        StatusMessage response = new StatusMessage("200", "OK");

        Optional<User> user = userRepo.findById(username);

        if (user.isPresent()) {
            userRepo.delete(user.get());
            return response;
        }

        response.setStatus(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        response.setMessage("Error deleting user");

        return response;
    }

    
}
