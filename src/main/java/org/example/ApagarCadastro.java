package org.example;

public class ApagarCadastro {

    private String op;
    private String user;
    private String pass;

    public ApagarCadastro(String user, String pass) {
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
}
