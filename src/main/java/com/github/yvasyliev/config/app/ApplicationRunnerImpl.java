package com.github.yvasyliev.config.app;

import com.github.yvasyliev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        
    }
}
