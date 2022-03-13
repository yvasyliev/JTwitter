package com.github.yvasyliev.controller;

import com.github.yvasyliev.model.dto.RegistrationForm;
import com.github.yvasyliev.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public String registrationView() {
        return "registration";
    }

    @PostMapping
    public String register(@RequestBody RegistrationForm registrationForm, Model model) {
        String confirmationToken = registrationService.register(registrationForm);
        model.addAttribute("confirmation_token", )
        return ;

    }
//
//    @GetMapping(path = "confirm")
//    public String confirm(@RequestParam("token") String token) {
//        return registrationService.confirmToken(token);
//    }
}
