package org.example;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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


        String token;
        if(u.getUsuario().equals("admin123") ) {
            token = "a" + (new Random().nextInt(90000) + 10000);
        }else {
            token = "c" + (new Random().nextInt(90000) + 10000);
        }

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

        String user = json.has("user") ? json.get("user").getAsString() : "";
        String pass = json.has("pass") ? json.get("pass").getAsString() : "";
        String newNick = json.has("new_nick") ? json.get("new_nick").getAsString() : "";
        String newPass = json.has("new_pass") ? json.get("new_pass").getAsString() : "";
        String token = json.has("token") ? json.get("token").getAsString() : "";

        if (user.isEmpty() || pass.isEmpty() || token.isEmpty()) {
            out.println(gson.toJson(new AlterarCadastroFailure("Usuário, senha ou token nulos")));
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

        if (!token.matches("[ac][0-9]+")) {
            out.println(gson.toJson(new AlterarCadastroFailure("Formato de token inválido")));
            return;
        }

        if (user.equals("admin123")) {
            out.println(gson.toJson(new AlterarCadastroFailure("A conta ADMIN não pode ser alterada")));
            return;
        }


        if (!newNick.isEmpty() && !newNick.matches("[a-zA-Z0-9 ]{6,16}")) {
            out.println(gson.toJson(new AlterarCadastroFailure("Formato de novo apelido inválido")));
            return;
        }

        if (!newPass.isEmpty() && !newPass.matches("[a-zA-Z0-9]{6,32}")) {
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

        if (!u.getToken().equals(token)) {
            out.println(gson.toJson(new AlterarCadastroFailure("Token inválido")));
            return;
        }

        String nickFinal = newNick.isEmpty() ? u.getApelido() : newNick;
        String passFinal = newPass.isEmpty() ? u.getSenha() : newPass;

        BancoUsuarios.atualizarUsuario(user, nickFinal, passFinal);
        out.println(gson.toJson(new AlterarCadastroSuccess()));
    }



    public static void handleApagarCadastro(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();
        String user = json.has("user") ? json.get("user").getAsString() : null;
        String token = json.has("token") ? json.get("token").getAsString() : null;
        String pass = json.has("pass") ? json.get("pass").getAsString() : null;

        if (user == null || pass == null || token == null || user.isEmpty() || pass.isEmpty() || token.isEmpty()) {
            out.println(gson.toJson(new ApagarCadastroFailure("Usuário, senha ou token nulos")));
            return;
        }

        if (!user.matches("[a-zA-Z0-9]{6,16}")) {
            out.println(gson.toJson(new ApagarCadastroFailure("Formato de usuário inválido")));
            return;
        }
        if (user.equals("admin123")) {
            out.println(gson.toJson(new ApagarCadastroFailure("A conta ADMIN não pode ser removida")));
            return;
        }


        if (!pass.matches("[a-zA-Z0-9]{6,32}")) {
            out.println(gson.toJson(new ApagarCadastroFailure("Formato de senha inválido")));
            return;
        }

        if (!token.matches("[ac][0-9]+")) {
            out.println(gson.toJson(new ApagarCadastroFailure("Formato de token inválido")));
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

        if (!u.getToken().equals(token)) {
            out.println(gson.toJson(new ApagarCadastroFailure("Token inválido")));
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

    public static void handleMensagemEnviar(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();

        String token = json.has("token") ? json.get("token").getAsString() : null;
        String id = json.has("id") ? json.get("id").getAsString() : null;
        String title = json.has("title") ? json.get("title").getAsString() : null;
        String subject = json.has("subject") ? json.get("subject").getAsString() : null;
        String msg = json.has("msg") ? json.get("msg").getAsString() : null;

        if (token == null || msg == null || title == null || subject == null ||
                token.isEmpty() || msg.isEmpty() || title.isEmpty() || subject.isEmpty()) {
            out.println(gson.toJson(new MensagemEnviarFailure("Campos obrigatórios ausentes")));
            return;
        }

        if (!token.matches("[ac][0-9]+")) {
            out.println(gson.toJson(new MensagemEnviarFailure("Formato de token inválido")));
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("mensagens.txt", true))) {
            bw.write((id == null ? "" : id) + ";" + title + ";" + subject + ";" + msg);
            bw.newLine();
            out.println(gson.toJson(new MensagemEnviarSuccess()));
        } catch (IOException e) {
            out.println(gson.toJson(new MensagemEnviarFailure("Falha ao gravar mensagem")));
        }
    }
    public static void handleMensagemReceber(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();
        String id = json.has("id") ? json.get("id").getAsString() : null;

        if (id == null || id.isEmpty()) {
            out.println(gson.toJson(new MensagemReceberFailure("Id nulo")));
            return;
        }

        if (!id.matches("[0-9]+(-[0-9]+)?")) {
            out.println(gson.toJson(new MensagemReceberFailure("Formato de id inválido")));
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("mensagens.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4 && id.equals(partes[0])) {
                    out.println(gson.toJson(new MensagemReceberSuccess(partes[3])));
                    return;
                }
            }
            out.println(gson.toJson(new MensagemReceberFailure("Mensagem não encontrada")));
        } catch (IOException e) {
            out.println(gson.toJson(new MensagemReceberFailure("Erro ao ler arquivo")));
        }
    }

    public static void handleRemoverUsuario(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();
        String token = json.has("token") ? json.get("token").getAsString() : "";
        String user = json.has("user") ? json.get("user").getAsString() : "";

        if (user.equals("admin123")) {
            out.println(gson.toJson(new AdminRemoveFailure("A conta ADMIN não pode ser removida")));
            return;
        }
        if (token.isEmpty() || user.isEmpty()) {
            out.println(gson.toJson(new AdminRemoveFailure("Token ou usuário ausente")));
            return;
        }

        if (!token.matches("a\\d+")) {
            out.println(gson.toJson(new AdminRemoveFailure("Apenas administradores podem remover usuários")));
            return;
        }

        if (!user.matches("[a-zA-Z0-9]{6,16}")) {
            out.println(gson.toJson(new AdminRemoveFailure("Formato de usuário inválido")));
            return;
        }

        if (!BancoUsuarios.usuarioExiste(user)) {
            out.println(gson.toJson(new AdminRemoveFailure("Usuário não existe")));
            return;
        }



        BancoUsuarios.removerUsuario(user);
        out.println(gson.toJson(new AdminRemoveSuccess()));
    }

    public static void handleAlterarUsuario(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();
        String token = json.has("token") ? json.get("token").getAsString() : "";
        String user = json.has("user") ? json.get("user").getAsString() : "";
        String newNick = json.has("new_nick") ? json.get("new_nick").getAsString() : "";
        String newPass = json.has("new_pass") ? json.get("new_pass").getAsString() : "";

        if(token.isEmpty() || user.isEmpty()) {
            out.println(gson.toJson(new AdminAlterarFailure("Token ou usuário ausente ")));
            return;
        }
        if (!token.matches("a\\d+")) {
            out.println(gson.toJson(new AdminAlterarFailure("Apenas administradores podem alterar usuários")));
            return;
        }

        if (!user.matches("[a-zA-Z0-9]{6,16}")) {
            out.println(gson.toJson(new AdminAlterarFailure("Formato de usuário inválido")));
            return;
        }
        if (!newNick.isEmpty() && !newNick.matches("[a-zA-Z0-9 ]{6,16}")) {
            out.println(gson.toJson(new AdminAlterarFailure("Formato de novo apelido inválido")));
            return;
        }

        if (!newPass.isEmpty() && !newPass.matches("[a-zA-Z0-9]{6,32}")) {
            out.println(gson.toJson(new AdminAlterarFailure("Formato de nova senha inválido")));
            return;
        }
        if (user.equals("admin123")) {
            out.println(gson.toJson(new AdminAlterarFailure("A conta ADMIN não pode ser alterada")));
            return;
        }


        Usuario u = BancoUsuarios.getUsuario(user);
        if (u == null) {
            out.println(gson.toJson(new AdminAlterarFailure("Usuário não existe")));
            return;
        }



        String nickFinal = newNick.isEmpty() ? u.getApelido() : newNick;
        String passFinal = newPass.isEmpty() ? u.getSenha() : newPass;

        BancoUsuarios.atualizarUsuario(user, nickFinal, passFinal);
        out.println(gson.toJson(new AdminAlterarSuccess()));


    }

    public static void handleBuscarCadastro(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();

        String user = json.has("user") ? json.get("user").getAsString() : "";
        String token = json.has("token") ? json.get("token").getAsString() : "";

        if (user.isEmpty() || token.isEmpty()) {
            out.println(gson.toJson(new BuscarCadastroResponseFailure("Usuário ou token nulo")));
            return;
        }

        Usuario u = BancoUsuarios.getUsuario(user);

        if (u == null) {
            out.println(gson.toJson(new BuscarCadastroResponseFailure("Usuário não existe")));
            return;
        }

        if (!token.equals(u.getToken())) {
            out.println(gson.toJson(new BuscarCadastroResponseFailure("Token inválido")));
            return;
        }

        out.println(gson.toJson(new BuscarCadastroResponseSuccess(u.getUsuario(), u.getApelido())));

    }
    public static void handleListarTodosUsuarios(JsonObject json, PrintWriter out) {
        Gson gson = new Gson();

        String token = json.has("token") ? json.get("token").getAsString() : "";

        if (token.isEmpty()) {
            out.println(gson.toJson(new BuscarTodosFailure("Token ausente")));
            return;
        }

        if (!token.matches("a\\d+")) {
            out.println(gson.toJson(new BuscarTodosFailure("Apenas administradores podem listar usuários")));
            return;
        }

        Collection<Usuario> todos = BancoUsuarios.getTodosUsuarios();

        List<String> lista = new ArrayList<>();
        for (Usuario u : todos) {
            lista.add(u.getUsuario());
        }

        out.println(gson.toJson(new BuscarTodosSucess(lista)));
    }






}