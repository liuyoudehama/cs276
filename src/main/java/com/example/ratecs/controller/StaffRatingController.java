package com.example.ratecs.controller;

import com.example.ratecs.design.StaffMemberProfileFactory;
import com.example.ratecs.model.RoleType;
import com.example.ratecs.model.StaffRating;
import com.example.ratecs.service.DuplicateEmailException;
import com.example.ratecs.service.StaffRatingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ratings")
public class StaffRatingController {

    private final StaffRatingService staffRatingService;
    private final StaffMemberProfileFactory profileFactory;

    public StaffRatingController(StaffRatingService staffRatingService, StaffMemberProfileFactory profileFactory) {
        this.staffRatingService = staffRatingService;
        this.profileFactory = profileFactory;
    }

    @GetMapping
    public String index(Model model) {
        List<StaffRatingView> ratings = staffRatingService.findAll().stream()
                .map(this::toView)
                .toList();
        model.addAttribute("ratings", ratings);
        return "ratings/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        StaffRating rating = staffRatingService.findById(id);
        model.addAttribute("ratingView", toView(rating));
        return "ratings/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staffRating", new StaffRating());
        model.addAttribute("isEdit", false);
        return "ratings/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("staffRating") StaffRating staffRating,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "ratings/form";
        }

        try {
            StaffRating created = staffRatingService.create(staffRating);
            return "redirect:/ratings/" + created.getId();
        } catch (DuplicateEmailException ex) {
            bindingResult.rejectValue("email", "duplicate", ex.getMessage());
            model.addAttribute("isEdit", false);
            return "ratings/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("staffRating", staffRatingService.findById(id));
        model.addAttribute("isEdit", true);
        return "ratings/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("staffRating") StaffRating staffRating,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            staffRating.setId(id);
            model.addAttribute("isEdit", true);
            return "ratings/form";
        }

        try {
            StaffRating updated = staffRatingService.update(id, staffRating);
            return "redirect:/ratings/" + updated.getId();
        } catch (DuplicateEmailException ex) {
            bindingResult.rejectValue("email", "duplicate", ex.getMessage());
            staffRating.setId(id);
            model.addAttribute("isEdit", true);
            return "ratings/form";
        }
    }

    @GetMapping("/{id}/delete")
    public String confirmDelete(@PathVariable Long id, Model model) {
        model.addAttribute("ratingView", toView(staffRatingService.findById(id)));
        return "ratings/delete-confirm";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        staffRatingService.delete(id);
        return "redirect:/ratings";
    }

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/ratings";
    }

    @ModelAttribute("roleTypes")
    public RoleType[] roleTypes() {
        return RoleType.values();
    }

    private StaffRatingView toView(StaffRating rating) {
        String displayTitle = profileFactory.create(rating.getRoleType()).displayTitle();
        return new StaffRatingView(rating, displayTitle);
    }
}
