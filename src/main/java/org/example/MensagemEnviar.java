package org.example;

public class MensagemEnviar {

    private String op;
    private String token;
    private String id;

    private String title;

    private String subject;
    private String msg;

    public MensagemEnviar( String token, String id, String title, String subject, String msg) {
        this.token = token;
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.msg = msg;
        this.op = "050";
    }

    public String getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
    public String getOp() {
        return op;
    }
    public String getToken() {
        return token;
    }
    public String getTitle() {
        return title;
    }
    public String getSubject() {
        return subject;
    }
}
