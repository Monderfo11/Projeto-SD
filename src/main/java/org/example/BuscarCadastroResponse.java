package org.example;

public class BuscarCadastroResponse {

    private String op = "013";
    private String nick;

    public BuscarCadastroResponse(String nick) {
        this.nick = nick;
    }

    public String getOp() { return op; }
    public String getNick() { return nick; }
}
