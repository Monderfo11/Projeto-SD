package org.example;

public class MensagemReceberSuccess {

    private String op = "061";
    private String msg;

    public MensagemReceberSuccess(String msg) {
        this.msg = msg;
    }

    public String getOp() {
        return op;
    }

    public String getMsg() {
        return msg;
    }
}
