package com.esoa.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/front")
public class FrontController {
    
    @GetMapping("developer")
    public ModelAndView getDeveloper() {
        return new ModelAndView("developer");
    }
    @GetMapping("desarrolladores")
    public ModelAndView getDesarrolladores() {
        return new ModelAndView("desarrolladores");
    }
}
