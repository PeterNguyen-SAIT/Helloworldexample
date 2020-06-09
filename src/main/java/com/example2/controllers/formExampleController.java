package com.example2.controllers;

import com.example2.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class formExampleController {

    @GetMapping("/registerExample")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);

        return "formExample";
    }

    @PostMapping("/registerExample")
    public String submitForm(@ModelAttribute("user") User user) {
        System.out.println(user);
        return "formExampleSuccess";
    }
}