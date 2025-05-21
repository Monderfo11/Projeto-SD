package org.example;

import java.io.*;
import java.util.*;

public class BancoUsuarios {
    private static final String ARQUIVO = "usuarios.txt";
    private static Map<String, Usuario> usuarios = new HashMap<>();

    static {
        carregarUsuarios();
    }

    private static void carregarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    String user = partes[0];
                    String nick = partes[1];
                    String pass = partes[2];
                    String token = partes.length >= 4 ? partes[3] : "";
                    usuarios.put(user, new Usuario(user, nick, pass, token));
                }
            }
        } catch (FileNotFoundException e) {
            // OK
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void salvarUsuario(Usuario u) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            bw.write(u.getUsuario() + ";" + u.getApelido() + ";" + u.getSenha() + ";" + u.getToken());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean usuarioExiste(String user) {
        return usuarios.containsKey(user);
    }

    public static Usuario getUsuario(String user) {
        return usuarios.get(user);
    }

    public static void adicionarUsuario(String user, String nick, String pass) {
        Usuario novo = new Usuario(user, nick, pass, "");
        usuarios.put(user, novo);
        salvarUsuario(novo);
    }

    public static void atualizarToken(String user, String token) {
        Usuario u = usuarios.get(user);
        if (u != null) {
            Usuario atualizado = new Usuario(u.getUsuario(), u.getApelido(), u.getSenha(), token);
            usuarios.put(user, atualizado);
            reescreverArquivo();
        }
    }

    private static void reescreverArquivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Usuario u : usuarios.values()) {
                bw.write(u.getUsuario() + ";" + u.getApelido() + ";" + u.getSenha() + ";" + u.getToken());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removerUsuario(String user) {
        usuarios.remove(user);
        reescreverArquivo();
    }




}