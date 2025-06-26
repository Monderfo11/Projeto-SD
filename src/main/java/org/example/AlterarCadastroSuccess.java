package org.example;

public class AlterarCadastroSuccess {

    private String op = "031";
    private String msg;

    public AlterarCadastroSuccess(String msg) {
        this.msg = msg;
    }


    public String getMsg() {

        return msg;
    }

    public String getOp() {
        return op;
    }
}
