
package com.esoa.demo.controller;

import com.esoa.demo.entity.Animal;
import com.esoa.demo.service.AnimalService;
import com.esoa.demo.service.SpecieService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final SpecieService specieService;
    
    @Secured({ "ADMIN", "USER" })
    @GetMapping
    public ModelAndView getAnimals(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("animal-table");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("success", inputFlashMap.get("success"));

        mav.addObject("animals", animalService.getAll());
        return mav;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public ModelAndView getAnimalForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("animal-form");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

         if (inputFlashMap != null) {
            mav.addObject("animal", inputFlashMap.get("animal"));
            mav.addObject("exception", inputFlashMap.get("exception"));
        } else {
            mav.addObject("animal", new Animal());
        }

        mav.addObject("species", specieService.getAll());
        mav.addObject("action", "create");
        return mav;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form/{id}")
    public ModelAndView getParkForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("animal-form");
        mav.addObject("animal", animalService.getById(id));

        mav.addObject("species", specieService.getAll());
        mav.addObject("action", "update");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public RedirectView create(Animal dto, @RequestParam(required = false) MultipartFile photo, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/animals");

        try {
            animalService.create(dto,photo);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("animal", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/animals/form");
        }

        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView update(Animal dto,@RequestParam(required = false) MultipartFile photo, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/animals");
        animalService.update(dto,photo);
        attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enable/{id}")
    public RedirectView enable(@PathVariable Integer id) {
        animalService.enableById(id);
        return new RedirectView("/animals");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        RedirectView redirect = new RedirectView("/animals");
        animalService.deleteById(id);
        return redirect;
    }
    //agregar la parte de eliminados
    //agregar el listado
}
