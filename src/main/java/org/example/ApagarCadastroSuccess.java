package org.example;

public class ApagarCadastroSuccess {

    private String op = "041";
    private String msg;

    public ApagarCadastroSuccess(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public String getOp() {
        return op;
    }
}
