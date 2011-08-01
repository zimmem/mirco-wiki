package com.zimmem.gae.wiki.viewtools;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GaeUserTools {

    private UserService userService = UserServiceFactory.getUserService();

    public boolean isLogin() {
        return userService.isUserLoggedIn();
    }

    public String getNickname() {
        User user = getCurrentUser();
        return user == null ? "" : user.getNickname();
    }

    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    public String getLogoutUrl() {
        return userService.createLogoutURL("");
    }

}
