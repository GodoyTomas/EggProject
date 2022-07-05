
package com.esoa.demo.controller;

import com.esoa.demo.entity.Contact;
import com.esoa.demo.entity.User;
import com.esoa.demo.service.ContactService;
import com.esoa.demo.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ModelAndView getContact(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("contact-table");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null)
            mav.addObject("success", inputFlashMap.get("success"));

        mav.addObject("contacts", contactService.getAll());
        return mav;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/form")
    public ModelAndView getContactForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("contact-form");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            mav.addObject("contact", inputFlashMap.get("contact"));
            mav.addObject("exception", inputFlashMap.get("exception"));
        } else {
            mav.addObject("contact", new Contact());
        }

        mav.addObject("action", "create");
        return mav;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public RedirectView create(Contact dto, RedirectAttributes attributes, Authentication auth) {
        RedirectView redirect = new RedirectView("/");

        try {
            contactService.create(dto, userService.getByEmail(auth.getName()));
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("contact", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/contacts/form");
        }

        return redirect;
    }

    
}
