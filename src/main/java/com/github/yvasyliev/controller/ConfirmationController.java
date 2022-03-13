package com.github.yvasyliev.controller;

import com.github.yvasyliev.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/confirmation")
public class ConfirmationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public String confirmationView(@RequestParam String token) {
        String confirmToken = registrationService.confirmToken(token);
        System.out.println(confirmToken);
        return "confirmation";
    }
}
