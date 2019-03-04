package com.example.uplift;

public class RegisterDataModel {

    String name, type, number, email, password;

    public RegisterDataModel(String name, String type, String number, String email, String password) {
        this.name = name;
        this.type = type;
        this.number = number;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
