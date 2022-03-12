package com.github.yvasyliev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/secured**")
public class SecuredController {
    @GetMapping
    public String getSecured(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "secured";
    }
}
