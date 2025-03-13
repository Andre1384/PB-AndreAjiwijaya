package com.example.myapplication;

public class UserDetails {

    private String userId;
    private String username;
    private String userEmail;
    private String userNIM;

    // Konstruktor default (diperlukan oleh Firebase)
    public UserDetails() {
    }

    // Konstruktor dengan parameter (tanpa password)
    public UserDetails(String userId, String username, String userEmail, String userNIM) {
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.userNIM = userNIM;
    }

    // Getter dan Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNIM() {
        return userNIM;
    }

    public void setUserNIM(String userNIM) {
        this.userNIM = userNIM;
    }
}
