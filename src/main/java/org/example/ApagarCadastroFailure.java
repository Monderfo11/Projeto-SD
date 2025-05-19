package org.example;

public class ApagarCadastroFailure {

    private String op = "042";
    private String msg;

    public ApagarCadastroFailure(String msg) {


        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getOp() {
        return op;
    }
}
