package com.github.yvasyliev.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private Function<String, Resource> imageResourceFactory;

    @GetMapping("/**")
    public ResponseEntity<Resource> getImage(HttpServletRequest request) {
        var resource = imageResourceFactory.apply(request.getRequestURI());
        var mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .body(resource);
    }
}
