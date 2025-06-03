package org.example;

public class AdminRemove {

    private String op;

    private String token;

    private String user;

    public AdminRemove( String token, String user) {
        this.token = token;
        this.user = user;
        this.op = "090";
    }

    public String getOp() {
        return op;
    }

    public String getToken() {
        return token;
    }

    public String getUser() {
        return user;
    }
}
