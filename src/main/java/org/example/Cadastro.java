package org.example;

public class Cadastro {
    private String op;
    private String user;

    private String pass;
    private String nick;

   public Cadastro(String user, String nick, String pass) {

       this.user = user;
       this.nick = nick;
       this.pass = pass;
       this.op = "010";

   }

    public String getNick() {
        return nick;
    }

    public String getOp() {
        return op;
    }

    public String getPass() {
        return pass;
    }

    public String getUser() {
        return user;
    }
}
