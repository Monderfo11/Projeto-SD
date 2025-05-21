package org.example;

public class LogoutFailure {

    private String op="022";
    private String msg;

    public LogoutFailure(String msg){
        this.msg=msg;
    }

    public String getOp() {
        return op;
    }

    public String getMsg() {
        return msg;
    }
}
