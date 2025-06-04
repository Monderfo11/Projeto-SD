package org.example;

public class BuscarCadastroResponseSuccess {
    private String op = "006";
    private String user;
    private String nick;

    public BuscarCadastroResponseSuccess(String user, String nick) {
        this.user = user;
        this.nick = nick;
    }

    public String getOp() {
        return op;
    }

    public String getUser() {
        return user;
    }

    public String getNick() {
        return nick;
    }
}
