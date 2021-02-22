package com.nervelife.soma.riyadhmetronetworkserver.configuration.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nervelife.soma.riyadhmetronetworkserver.domain.models.StatusMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    static final String APP_JSON = "application/json";

    @Autowired
    ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
            AuthenticationException exception) throws IOException, ServletException {

        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null){
            statusCode = HttpStatus.UNAUTHORIZED.value();
        }
        
        resp.setStatus(statusCode);
		resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        String contentType = req.getHeader("Content-Type");
        String returnType = req.getHeader("Return-Type");

        if (contentType != null && contentType.equals(APP_JSON) || 
                (returnType != null && returnType.equals(APP_JSON))) {

            resp.setContentType(APP_JSON);
            out.print( mapper.writeValueAsString(new StatusMessage(statusCode.toString(), exception.getMessage())) );
        }else{
            resp.sendRedirect("/login?status="+statusCode+"&message='"+exception.getMessage()+"'");
        }

        out.flush();
		out.close();	

    }

}