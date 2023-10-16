package com.vmish.taskmanager.model;


public class Auth {
    private String login;
    private String pass;


    public Auth() {

    }

    public Auth(String login, String password) {
        this.login = login;
        this.pass = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
