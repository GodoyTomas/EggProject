
package com.esoa.demo.controller;

import com.esoa.demo.entity.Post;
import com.esoa.demo.service.AnimalService;
import com.esoa.demo.service.ParkService;
import com.esoa.demo.service.PostService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.annotation.Secured;
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
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final AnimalService animalService;
    private final ParkService parkService;


    @GetMapping
    public ModelAndView getPost(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("post-table");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("success", inputFlashMap.get("success"));

        mav.addObject("posts", postService.getAll());
        return mav;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public ModelAndView getPostForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("post-form");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("post", inputFlashMap.get("post"));
            mav.addObject("exception", inputFlashMap.get("exception"));
        } else {
            mav.addObject("post", new Post());
        }
        mav.addObject("animals",animalService.getAll());
        mav.addObject("parks",parkService.getAll());
        mav.addObject("action", "create");
        return mav;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form/{id}")
    public ModelAndView getPostForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("post-form");
        mav.addObject("post", postService.getById(id));
        mav.addObject("animals",animalService.getAll());
        mav.addObject("parks",parkService.getAll());
        mav.addObject("action", "update");
        return mav;
    }

    @Secured({ "ADMIN", "USER" })
    @GetMapping("/detail/{id}")
    public ModelAndView getDetails(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("post-detail");
        Post post = new Post();
        post= postService.getById(id);
        mav.addObject("post", post);
        mav.addObject("listPost", postService.getAll());
        if (post.getAnimal()!=null) mav.addObject("animal",animalService.getById(post.getAnimal().getId()));
        if (post.getPark()!=null) mav.addObject("park",parkService.getById(post.getPark().getId()));
        mav.addObject("action", "detail");
        mav.addObject("action1", "vote");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public RedirectView create(Post dto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/posts");

        try {
            postService.create(dto);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("post", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/posts/form");
        }

        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView update(Post dto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/posts");
        postService.update(dto);
        attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enable/{id}")
    public RedirectView enable(@PathVariable Integer id) {
        postService.enableById(id);
        return new RedirectView("/posts");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        RedirectView redirect = new RedirectView("/posts");
        postService.deleteById(id);
        return redirect;
    }


}