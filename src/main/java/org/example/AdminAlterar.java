package org.example;

public class AdminAlterar {

    private String op;
    private String token;

    private String user;

    private String new_nick;
    private String new_pass;

    public AdminAlterar(String token, String user, String new_nick, String new_pass) {
        this.token = token;
        this.user = user;
        this.new_nick = new_nick;
        this.new_pass = new_pass;
        this.op= "080";
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

    public String getNew_nick() {
        return new_nick;
    }

    public String getNew_pass() {
        return new_pass;
    }
}
