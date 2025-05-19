package org.example;

public class CadastroResponseFailure {

    private String op = "012";
    private String msg;

    public CadastroResponseFailure(String msg) {
        this.msg = msg;
    }

    public String getOp() {
        return op;
    }

    public String getMsg() {
        return msg;
    }
}
