package org.example;

public class LoginResponseFailure {
    private String op = "002";
    private String msg;

    public LoginResponseFailure(String msg) {
        this.msg = msg;
    }

    public String getOp() { return op; }
    public String getMsg() { return msg; }
}
