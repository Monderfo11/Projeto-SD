package org.example;

public class MensagemReceber {

    private String op;

    private String id;

    public MensagemReceber( String id) {

        this.id = id;
        this.op = "060";
    }

    public String getOp() {
        return op;
    }

    public String getId() {
        return id;
    }
}
