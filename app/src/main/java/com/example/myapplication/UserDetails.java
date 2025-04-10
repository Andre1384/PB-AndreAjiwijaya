package com.example.myapplication;

public class UserDetails {

    private String name;
    private String email;
    private String nim;

    public UserDetails() {
        // Diperlukan untuk Firebase
    }

    public UserDetails(String name, String email, String nim) {
        this.name = name;
        this.email = email;
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
