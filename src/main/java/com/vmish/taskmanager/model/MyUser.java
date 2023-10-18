package com.vmish.taskmanager.model;


import jakarta.persistence.*;

@Entity
public class MyUser {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    private Role role;
    public MyUser() {
    }

    public MyUser(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
