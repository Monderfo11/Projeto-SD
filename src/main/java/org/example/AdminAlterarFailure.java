package org.example;

public class AdminAlterarFailure {

    private String op = "082";

    private String msg ;

    public AdminAlterarFailure(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getOp() {
        return op;
    }
}
