package com.nervelife.soma.riyadhmetronetworkserver.configuration.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StatusMessage;
import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.User;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    static final String APP_JSON = "application/json";

    @Autowired
    ObjectMapper mapper;

    @Autowired
	UserRepository userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp,
            Authentication authentication) throws IOException, ServletException {
        
        resp.setStatus(HttpStatus.OK.value());
		resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        String contentType = req.getHeader("Content-Type");
        String returnType = req.getHeader("Return-Type");
        if (contentType != null && contentType.equals(APP_JSON) || 
                (returnType != null && returnType.equals(APP_JSON))) {

            resp.setContentType(APP_JSON);
            StatusMessage sm = new StatusMessage("200", "OK");
            org.springframework.security.core.userdetails.User usr = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            Optional<User> user = userRepo.findById(usr.getUsername());
            sm.setPayload(user);
            out.print( mapper.writeValueAsString( sm ) );
        }else{
            resp.sendRedirect("/");
        }

        out.flush();
		out.close();	

    }

}
