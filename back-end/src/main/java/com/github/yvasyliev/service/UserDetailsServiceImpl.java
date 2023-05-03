package com.github.yvasyliev.service;

import com.github.yvasyliev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String USER_NOT_FOUND_MSG = "User '%s' was not found.";

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByUsernameOrEmail(username, username);
    }

    public UserDetails loadUserByUsernameOrEmail(String username, String email) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmail(username, email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        USER_NOT_FOUND_MSG,
                        username != null ? username : email
                )));
    }
}
