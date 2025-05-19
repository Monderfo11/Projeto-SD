package org.example;

public class LoginResponseSuccess {

    private String op = "001";

    private String token;

    public LoginResponseSuccess(String token) {
        this.token = token;
    }

    public String getOp() {
        return op;
    }

    public String getToken() {
        return token;
    }
}
