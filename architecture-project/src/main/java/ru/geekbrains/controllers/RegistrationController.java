package ru.geekbrains.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.data.UserData;
import ru.geekbrains.entities.User;
import ru.geekbrains.services.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String registration(
            Model model
    ){
        model.addAttribute("userData", new UserData());
        return "registration";
    }

    @PostMapping
    public String registration(
            @Valid @ModelAttribute UserData userData,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        User user = userService.createUser(userData);
        userService.authenticateUser(user);
        return "redirect:/";
    }
}
