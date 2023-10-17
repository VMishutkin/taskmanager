package com.vmish.taskmanager.model;


import com.vmish.taskmanager.repository.UserRepository;

public class Auth {
    private String login;
    private Role role;


    public Auth() {


    }

    public Auth(String login, Role role) {
        this.login = login;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getRole() {
        return role.name();
    }
}
