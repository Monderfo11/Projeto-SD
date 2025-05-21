package org.example;

public class Logout {

    private String op;
    private String user;
    private String token;

    public Logout( String user, String token) {
        this.user = user;
        this.token = token;
        this.op="020";
    }
    public String getOp() {
        return op;
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
