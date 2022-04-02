package com.github.yvasyliev.model.event;

import com.github.yvasyliev.model.entity.user.User;
import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {
    private User user;
    private String url;

    public UserRegisteredEvent(User user, String url) {
        super(user);
        this.user = user;
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
