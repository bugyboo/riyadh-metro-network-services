package com.nervelife.soma.riyadhmetronetworkserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({ "/", "/index", "index.html", "/ar/home", "/en/home" })
    public String root() {
        return "app";
    }
    
}
