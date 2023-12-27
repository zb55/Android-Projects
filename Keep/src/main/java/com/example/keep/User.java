package com.example.keep;

public class User {
    private String number;
    private String password;
    private String course;
    public User(String number, String password) {
        this.number = number;
        this.password = password;
        course = new String("");
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
