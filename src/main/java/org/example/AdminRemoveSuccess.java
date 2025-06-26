package org.example;

public class AdminRemoveSuccess {

    private String op = "091";
    private String msg;

    public AdminRemoveSuccess(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public String getOp() {
        return op;
    }
}
