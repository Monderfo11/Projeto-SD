package org.example;

public class BuscarTodosFailure {
    private String op = "112";
    private String msg;

    public BuscarTodosFailure(String msg) {
        this.msg = msg;
    }

    public String getOp() {
        return op;
    }

    public String getMsg() {
        return msg;
    }
}
