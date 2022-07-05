
package com.esoa.demo.controller;

import com.esoa.demo.entity.Specie;
import com.esoa.demo.service.SpecieService;
import com.esoa.demo.service.ValorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ValorationController {

    private final ValorationService valorationService;

/*
    //    @PreAuthorize("hasRole('ADMIN')")
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

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView update(Specie dto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/species");
        specieService.update(dto);
        attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        return redirect;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enable/{id}")
    public RedirectView enable(@PathVariable Integer id) {
        specieService.enableById(id);
        return new RedirectView("/species");
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        RedirectView redirect = new RedirectView("/species");
        specieService.deleteById(id);
        return redirect;
    }
    */
}
