package com.github.yvasyliev.controller;

import com.github.yvasyliev.model.dto.RegistrationForm;
import com.github.yvasyliev.model.entity.ConfirmationToken;
import com.github.yvasyliev.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String register(RegistrationForm registrationForm, Model model) {
        ConfirmationToken confirmationToken = registrationService.register(registrationForm);
        model.addAttribute("email", registrationForm.getEmail());
        model.addAttribute("confirmation_token", confirmationToken.getToken());
        return "complete_registration";
    }

    @GetMapping("/complete")
    public String completeRegistration(@RequestParam String token) {
        String confirmToken = registrationService.completeRegistration(token);
        System.out.println(confirmToken);
        return "confirmation";
    }
}
