package com.github.yvasyliev.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LocalImageResourceFactory implements Function<String, Resource> {
    @Value("${imagesFolder:back-end}")
    private String imagesFolder;

    @Override
    public Resource apply(String requestedImage) {
        return new FileSystemResource(imagesFolder + requestedImage);
    }
}
