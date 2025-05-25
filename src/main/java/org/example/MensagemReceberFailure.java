package org.example;

public class MensagemReceberFailure {

    private String op = "062";
    private String msg;

    public MensagemReceberFailure(String msg) {
        this.msg = msg;
    }
    public String getOp() {
        return op;
    }

    public String getMsg() {
        return msg;
    }
}
