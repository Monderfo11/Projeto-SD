package org.example;

public class Usuario {
    private String user;
    private String pass;
    private String nick;
    private String token;

    public Usuario(String user, String nick, String pass, String token) {
        this.user = user;
        this.nick = nick;
        this.pass = pass;
        this.token = token;
    }

    public String getUsuario() { return user; }
    public String getSenha() { return pass; }
    public String getApelido() { return nick; }

    public String getToken() {
        return token;
    }


}
