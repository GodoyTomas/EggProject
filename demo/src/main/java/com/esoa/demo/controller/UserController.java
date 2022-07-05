
package com.esoa.demo.controller;

import com.esoa.demo.entity.User;
import com.esoa.demo.enumeration.Role;
import com.esoa.demo.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ModelAndView getUsers(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("user-table");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("success", inputFlashMap.get("success"));

        mav.addObject("users", userService.getAll());
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public ModelAndView getForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("user-form");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("user", inputFlashMap.get("user"));
            mav.addObject("exception", inputFlashMap.get("exception"));
        } else {
            mav.addObject("user", new User());
        }

        mav.addObject("action", "create");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form/{id}")
    public ModelAndView getForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("user-form");
        mav.addObject("user", userService.getById(id));
        mav.addObject("action", "update");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public RedirectView create(User dto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/users");

        try {
            userService.create(dto);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("user", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/users/form");
        }

        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView update(User dto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/users");
        userService.update(dto);
        attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        return redirect;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enable/{id}")
    public RedirectView enable(@PathVariable Integer id) {
        userService.enableById(id);
        return new RedirectView("/users");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        RedirectView redirect = new RedirectView("/users");
        userService.deleteById(id);
        return redirect;
    }
}
