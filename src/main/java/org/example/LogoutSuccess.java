package org.example;

public class LogoutSuccess {

    private String op="021";

    private String msg;

    public LogoutSuccess(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public String getOp() {
        return op;
    }
}
