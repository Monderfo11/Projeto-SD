package org.example;

public class Login {

    private String op ;

    private String user;

    private String pass;

    public Login(String user, String pass) {
        this.op = "000";
        this.user = user;
        this.pass = pass;
    }

    public String getOp() { return op; }
    public String getUser() { return user; }
    public String getPass() { return pass; }
}
