package org.example;

public class AlterarCadastro {

        private String op;
        private String user;
        private String pass;
        private String new_nick;
        private String new_pass;
        private String token;

        public AlterarCadastro(String user, String pass, String new_nick, String new_pass,String token) {
            this.op = "030";

            this.user = user;
            this.pass = pass;
            this.new_nick = new_nick;
            this.new_pass = new_pass;
            this.token = token;

        }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getOp() {
        return op;
    }

    public String getNew_nick() {
        return new_nick;
    }

    public String getNew_pass() {
        return new_pass;
    }

    public String getToken() {
        return token;
    }
}
