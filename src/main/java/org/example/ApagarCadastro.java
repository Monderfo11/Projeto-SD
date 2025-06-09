package org.example;

public class ApagarCadastro {

    private String op;
    private String user;
    private String token;
    private String pass;

    public ApagarCadastro(String user, String token,String pass) {
        this.user = user;
        this.pass = pass;
        this.op = "040";

    }

    public String getOp() {
        return op;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getToken() {
        return token;
    }
}
