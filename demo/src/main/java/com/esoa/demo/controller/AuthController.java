package com.esoa.demo.controller;

import com.esoa.demo.entity.User;
import com.esoa.demo.enumeration.Role;
import com.esoa.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {
        ModelAndView mav = new ModelAndView("login-form");

        if (error != null) mav.addObject("error", "Email o contraseña incorrecta");
        if (logout != null) mav.addObject("logout", "Saliste de la plataforma");
        if (principal != null) mav.setViewName("redirect:/");

        return mav;
    }

    @GetMapping("/sign-up")
    public ModelAndView signup(HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("user-form");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (principal != null) mav.setViewName("redirect:/");

        if (inputFlashMap != null) {
            mav.addObject("exception", inputFlashMap.get("exception"));
            mav.addObject("user", inputFlashMap.get("user"));
        } else {
            User user = new User();
            user.setRole(Role.USER);
            mav.addObject("user", user);
        }
        mav.addObject("action", "create");
        return mav;
    }

    @PostMapping("/register")
    public RedirectView signup(User dto, HttpServletRequest request, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/");

        try {
            userService.create(dto);
            request.login(dto.getEmail(), dto.getPassword());
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("user", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/users/form");
        } catch (ServletException e) {
            attributes.addFlashAttribute("error", "Auto login failed");
        }

        return redirect;
    }
}
