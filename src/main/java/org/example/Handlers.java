package org.example;

import com.google.gson.*;
import java.io.PrintWriter;
import java.util.Random;

public class Handlers {

    public static void handleLogin(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();
        String user = json.has("user") ? json.get("user").getAsString() : null;
        String pass = json.has("pass") ? json.get("pass").getAsString() : null;

        if (user == null || pass == null || user.isEmpty() || pass.isEmpty()) {
            out.println(gson.toJson(new LoginResponseFailure("Usuário ou senha nulos")));
            return;
        }

        if (!user.matches("[a-zA-Z0-9]{6,16}")) {
            out.println(gson.toJson(new LoginResponseFailure("Formato de usuário inválido")));
            return;
        }

        if (!pass.matches("[a-zA-Z0-9]{6,32}")) {
            out.println(gson.toJson(new LoginResponseFailure("Formato de senha inválido")));
            return;
        }

        Usuario u = BancoUsuarios.getUsuario(user);
        if (u == null) {
            out.println(gson.toJson(new LoginResponseFailure("Usuário não encontrado")));
            return;
        }

        if (!u.getSenha().equals(pass)) {
            out.println(gson.toJson(new LoginResponseFailure("Senha incorreta")));
            return;
        }

        String token = "c" + (new Random().nextInt(90000) + 10000);
        BancoUsuarios.atualizarToken(user, token);
        out.println(gson.toJson(new LoginResponseSuccess(token)));
    }

    public static void handleCadastro(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();
        String user = json.has("user") ? json.get("user").getAsString() : null;
        String nick = json.has("nick") ? json.get("nick").getAsString() : null;
        String pass = json.has("pass") ? json.get("pass").getAsString() : null;

        if (user == null || nick == null || pass == null) {
            out.println(gson.toJson(new CadastroResponseFailure("Campos obrigatórios faltando")));
            return;
        }

        if (!user.matches("[a-zA-Z0-9]{6,16}")) {
            out.println(gson.toJson(new CadastroResponseFailure("Formato de usuário inválido")));
            return;
        }

        if (!nick.matches("[a-zA-Z0-9 ]{6,16}")) {
            out.println(gson.toJson(new CadastroResponseFailure("Formato de apelido inválido")));
            return;
        }

        if (!pass.matches("[a-zA-Z0-9]{6,32}")) {
            out.println(gson.toJson(new CadastroResponseFailure("Formato de senha inválido")));
            return;
        }

        if (BancoUsuarios.usuarioExiste(user)) {
            out.println(gson.toJson(new CadastroResponseFailure("Usuário já existe")));
            return;
        }

        BancoUsuarios.adicionarUsuario(user, nick, pass);
        out.println(gson.toJson(new CadastroResponseSuccess()));
    }

    public static void handleAlterarCadastro(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();
        String user = json.has("user") ? json.get("user").getAsString() : null;
        String pass = json.has("pass") ? json.get("pass").getAsString() : null;
        String newNick = json.has("new_nick") ? json.get("new_nick").getAsString() : null;
        String newPass = json.has("new_pass") ? json.get("new_pass").getAsString() : null;

        if (user == null || pass == null) {
            out.println(gson.toJson(new AlterarCadastroFailure("Usuário ou senha nulos")));
            return;
        }

        if (!user.matches("[a-zA-Z0-9]{6,16}")) {
            out.println(gson.toJson(new AlterarCadastroFailure("Formato de usuário inválido")));
            return;
        }

        if (!pass.matches("[a-zA-Z0-9]{6,32}")) {
            out.println(gson.toJson(new AlterarCadastroFailure("Formato de senha inválido")));
            return;
        }

        if (newNick != null && !newNick.isEmpty() && !newNick.matches("[a-zA-Z0-9 ]{6,16}")) {
            out.println(gson.toJson(new AlterarCadastroFailure("Formato de novo apelido inválido")));
            return;
        }

        if (newPass != null && !newPass.isEmpty() && !newPass.matches("[a-zA-Z0-9]{6,32}")) {
            out.println(gson.toJson(new AlterarCadastroFailure("Formato de nova senha inválido")));
            return;
        }

        Usuario u = BancoUsuarios.getUsuario(user);
        if (u == null) {
            out.println(gson.toJson(new AlterarCadastroFailure("Usuário não existe")));
            return;
        }

        if (!u.getSenha().equals(pass)) {
            out.println(gson.toJson(new AlterarCadastroFailure("Senha incorreta")));
            return;
        }

        String nickFinal = (newNick == null || newNick.isEmpty()) ? u.getApelido() : newNick;
        String passFinal = (newPass == null || newPass.isEmpty()) ? u.getSenha() : newPass;

        BancoUsuarios.adicionarUsuario(user, nickFinal, passFinal); // Sobrescreve
        out.println(gson.toJson(new CadastroResponseSuccess()));
    }
    public static void handleApagarCadastro(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();
        String user = json.has("user") ? json.get("user").getAsString() : null;
        String pass = json.has("pass") ? json.get("pass").getAsString() : null;

        if (user == null || pass == null || user.isEmpty() || pass.isEmpty()) {
            out.println(gson.toJson(new ApagarCadastroFailure("Usuário ou senha nulos")));
            return;
        }

        if (!user.matches("[a-zA-Z0-9]{6,16}")) {
            out.println(gson.toJson(new ApagarCadastroFailure("Formato de usuário inválido")));
            return;
        }

        if (!pass.matches("[a-zA-Z0-9]{6,32}")) {
            out.println(gson.toJson(new ApagarCadastroFailure("Formato de senha inválido")));
            return;
        }

        Usuario u = BancoUsuarios.getUsuario(user);
        if (u == null) {
            out.println(gson.toJson(new ApagarCadastroFailure("Usuário não existe")));
            return;
        }

        if (!u.getSenha().equals(pass)) {
            out.println(gson.toJson(new ApagarCadastroFailure("Senha incorreta")));
            return;
        }

        BancoUsuarios.removerUsuario(user);
        out.println(gson.toJson(new ApagarCadastroSuccess()));
    }

    public static void handleRealizarLogout(JsonObject json, PrintWriter out) {

        Gson gson = new Gson();
        String user = json.has("user") ? json.get("user").getAsString() : null;

        String token = json.has("token") ? json.get("token").getAsString() : null;

        if (user == null || token == null || user.isEmpty() || token.isEmpty()) {
            out.println(gson.toJson(new LogoutFailure("Usuário ou token nulo")));
            return;
        }
        if (!user.matches("[a-zA-Z0-9]{6,16}")) {
            out.println(gson.toJson(new LogoutFailure("Formato de usuário inválido")));
            return;
        }
        if (!token.matches("[ac][0-9]+")) {
            out.println(gson.toJson(new LogoutFailure("Formato de token inválido")));
            return;
        }


        Usuario u = BancoUsuarios.getUsuario(user);

        if (u == null) {
            out.println(gson.toJson(new LogoutFailure("Usuário não existe")));
            return;

        }

        if(!u.getToken().equals(token)) {
            out.println(gson.toJson(new LogoutFailure("Token é de outro usuário")));
            return;

        }

        BancoUsuarios.atualizarToken(user, "");

        out.println(gson.toJson(new LogoutSuccess()));



    }
}