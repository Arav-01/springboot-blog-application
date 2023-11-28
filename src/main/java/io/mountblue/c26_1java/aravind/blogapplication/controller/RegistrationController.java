package io.mountblue.c26_1java.aravind.blogapplication.controller;

import io.mountblue.c26_1java.aravind.blogapplication.model.User;
import io.mountblue.c26_1java.aravind.blogapplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blog-application")
public class RegistrationController {
    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("newUser", new User());

        return "registration-form";
    }

    @PostMapping("/register/processRegistration")
    public String processRegistration(@Valid @ModelAttribute("newUser") User newUser,
                                      BindingResult bindingResult,
                                      @RequestParam(required = false) String passwordRepeat,
                                      Model model) {
        if (bindingResult.hasErrors()) {
            return "registration-form";
        }

        if (!newUser.getPassword().equals(passwordRepeat)) {
            model.addAttribute("newUser", newUser);
            model.addAttribute("registrationError", "Passwords do not match");

            return "registration-form";
        }

        boolean userAlreadyExists = userService.findByEmail(newUser.getEmail()) != null;
        if (userAlreadyExists){
            model.addAttribute("newUser", newUser);
            model.addAttribute("registrationError",
                    "Email already exists. Enter a different email");

            return "registration-form";
        }

        userService.save(newUser);

        return "redirect:/blog-application/login";
    }
}
