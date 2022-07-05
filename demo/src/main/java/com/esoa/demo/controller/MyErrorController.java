package com.esoa.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Controller
public class MyErrorController implements ErrorController {
    
    
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        Integer status = (int) request.getAttribute(ERROR_STATUS_CODE);
        String message;

        switch (status) {
            case 403:
                message = "No tiene los permisos para acceder";
                break;
            case 404:
                message = "No se encontro lo que solicitó";
                break;
            case 500:
                message = "Error de servidor";
                break;
            default:
                message = "Error inesperado";
        }

        String soporte = "Contacte con nosotros";
        mav.addObject("message", message);
        mav.addObject("status", status);
        mav.addObject("support", soporte);
        return mav;
    }

    
}

