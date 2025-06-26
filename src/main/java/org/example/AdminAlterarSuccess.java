package org.example;

public class AdminAlterarSuccess {

    private String op = "081";
    private String msg;

    public AdminAlterarSuccess(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public String getOp() {
        return op;
    }
}
