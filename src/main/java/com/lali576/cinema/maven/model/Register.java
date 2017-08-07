package com.lali576.cinema.maven.model;

public class Register {
    private final String username;
    private final boolean isAdmin;
    
    public Register(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }
    
    public String getName() {
        return username;
    }
    
    public boolean getIsAdmin() {
        return isAdmin;
    }
}
