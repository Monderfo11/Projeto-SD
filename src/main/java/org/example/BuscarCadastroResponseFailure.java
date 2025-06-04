package org.example;

public class BuscarCadastroResponseFailure {
    private String op = "007";
    private String msg;

    public BuscarCadastroResponseFailure(String msg) {
        this.msg = msg;

    }
    public String getOp() {
        return op;
    }

    public String getMsg() {
        return msg;
    }
}
