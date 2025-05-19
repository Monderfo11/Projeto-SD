package org.example;

public class AlterarCadastroFailure {

    private String op = "032";
    private String msg;

    public AlterarCadastroFailure(String msg) {
        this.msg = msg;
    }

    public String getOp() {
        return op;
    }

    public String getMsg() {
        return msg;
    }
}
