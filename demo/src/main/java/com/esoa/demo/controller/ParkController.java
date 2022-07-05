package com.esoa.demo.controller;

import com.esoa.demo.entity.Park;
import com.esoa.demo.service.ParkService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/parks")
@RequiredArgsConstructor
public class ParkController {

    private final ParkService parkService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ModelAndView getParks(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("park-table");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("success", inputFlashMap.get("success"));

        mav.addObject("parks", parkService.getAll());
        return mav;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public ModelAndView getParkForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("park-form");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("park", inputFlashMap.get("park"));
            mav.addObject("exception", inputFlashMap.get("exception"));
        } else {
            mav.addObject("park", new Park());
        }

        mav.addObject("action", "create");
        return mav;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form/{id}")
    public ModelAndView getParkForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("park-form");
        mav.addObject("park", parkService.getById(id));
        mav.addObject("action", "update");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public RedirectView create(Park dto, @RequestParam(required = false) MultipartFile photo, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/parks");

        try {
            parkService.create(dto,photo);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("park", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/parks/form");
        }

        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView update(Park dto, RedirectAttributes attributes, @RequestParam(required = false) MultipartFile photo) {
        RedirectView redirect = new RedirectView("/parks");
        parkService.update(dto, photo);
        attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enable/{id}")
    public RedirectView enable(@PathVariable Integer id) {
        parkService.enableById(id);
        return new RedirectView("/parks");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        RedirectView redirect = new RedirectView("/parks");
        parkService.deleteById(id);
        return redirect;
    }
    //agregar la parte de eliminados
    //agregar el listado
}
