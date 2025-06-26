package org.example;

public class CadastroResponseSuccess {

    private String op = "011";
    private String msg;

    public CadastroResponseSuccess(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public String getOp() {
        return op;
    }
}
