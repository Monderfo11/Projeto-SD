package org.example;

public class MensagemEnviarFailure {

    private String msg;

    private String op = "052";

    public MensagemEnviarFailure(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public String getOp() {
        return op;
    }
}
