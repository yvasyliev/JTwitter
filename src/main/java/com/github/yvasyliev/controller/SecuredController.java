package com.github.yvasyliev.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/secured**")
public class SecuredController {
    @GetMapping
    public String getSecured(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "secured";
    }
}
