package com.mine.manager.common;

import com.mine.manager.security.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UserContext {

    private User getUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User user) {
            return user;
        }
        return null;
    }

    public String getUsername() {
        User user = getUser();
        return (user != null) ? user.getUsername() : "SYSTEM";
    }

    public Integer getUserId() {
        User user = getUser();
        return (user != null) ? user.getId() : null;
    }
}