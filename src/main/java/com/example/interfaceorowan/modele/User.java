package com.example.interfaceorowan.modele;

public class User {
    private static User instance;
    private String name;
    private String role;

    private User(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public static User getInstance(String name, String role) {
        if (instance == null) {
            instance = new User(name, role);
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

