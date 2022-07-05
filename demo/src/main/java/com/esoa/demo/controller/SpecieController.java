
package com.esoa.demo.controller;

import com.esoa.demo.entity.Specie;
import com.esoa.demo.service.SpecieService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/species")
@RequiredArgsConstructor
public class SpecieController {

    private final SpecieService specieService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ModelAndView getSpecies(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("specie-table"); //ver nombre de hmtl
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("success", inputFlashMap.get("success"));

        mav.addObject("species", specieService.getAll());
        return mav;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public ModelAndView getSpecieForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("specie-form");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("specie", inputFlashMap.get("specie"));
            mav.addObject("exception", inputFlashMap.get("exception"));
        } else {
            mav.addObject("specie", new Specie());
        }

        mav.addObject("action", "create");
        return mav;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form/{id}")
    public ModelAndView getSpecieForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("specie-form");
        mav.addObject("specie", specieService.getById(id));
        mav.addObject("action", "update");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public RedirectView create(Specie dto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/species");

        try {
            specieService.create(dto);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("specie", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/species/form");
        }

        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView update(Specie dto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/species");
        specieService.update(dto);
        attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enable/{id}")
    public RedirectView enable(@PathVariable Integer id) {
        specieService.enableById(id);
        return new RedirectView("/species");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        RedirectView redirect = new RedirectView("/species");
        specieService.deleteById(id);
        return redirect;
    }
    //agregar la parte de eliminados
    //agregar el listado
}
