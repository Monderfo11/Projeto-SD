package org.example;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TcpClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("IP do servidor: ");
        String ip = scanner.nextLine();

        System.out.print("Porta do servidor: ");
        int porta = Integer.parseInt(scanner.nextLine());

        try (
                Socket socket = new Socket(ip, porta);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Conectado ao servidor.");

            while (true) {
                mostrarMenu();
                int opcao = Integer.parseInt(scanner.nextLine());

                JsonObject req = new JsonObject();

                switch (opcao) {
                    case 1: fazerLogin(req, scanner); break;
                    case 2: fazerCadastro(req, scanner); break;
                    case 3: fazerLogout(req, scanner); break;
                    case 4: alterarCadastro(req, scanner); break;
                    case 5: apagarCadastro(req, scanner); break;
                    case 6: buscarCadastro(req, scanner); break;
                    case 7: alterarCadastroAdmin(req, scanner); break;
                    case 8: removerCadastroAdmin(req, scanner); break;
                    case 9: buscarCadastradosAdmin(req, scanner); break;
                    case 0: System.out.println("Encerrando..."); return;
                    default: System.out.println("Opção inválida."); continue;
                }

                out.println(req.toString());

                String resposta = in.readLine();
                System.out.println("Resposta: " + resposta);

                // Captura token após login válido
                if (req.get("op").getAsString().equals("000")) {
                    JsonObject respJson = JsonParser.parseString(resposta).getAsJsonObject();
                    if (respJson.has("token")) {
                        // token e user só são armazenados internamente para referência, mas não usados nos próximos envios
                        String token = respJson.get("token").getAsString();
                        System.out.println("(Token recebido e armazenado internamente: " + token + ")");
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        System.out.println("\nMENU:");
        System.out.println("1 - Login");
        System.out.println("2 - Cadastro");
        System.out.println("3 - Logout");
        System.out.println("4 - Alterar Cadastro");
        System.out.println("5 - Apagar Cadastro");
        System.out.println("6 - Buscar Cadastro");
        System.out.println("7 - Alterar Cadastro - ADMIN");
        System.out.println("8 - Remover Cadastro - ADMIN");
        System.out.println("9 - Listar Usuários - ADMIN ");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private static void fazerLogin(JsonObject req, Scanner scanner) {
        req.addProperty("op", "000");
        System.out.print("Usuário: ");
        req.addProperty("user", scanner.nextLine());
        System.out.print("Senha: ");
        req.addProperty("pass", scanner.nextLine());
    }

    private static void fazerCadastro(JsonObject req, Scanner scanner) {
        req.addProperty("op", "010");
        System.out.print("Usuário: ");
        req.addProperty("user", scanner.nextLine());
        System.out.print("Apelido: ");
        req.addProperty("nick", scanner.nextLine());
        System.out.print("Senha: ");
        req.addProperty("pass", scanner.nextLine());
    }

    private static void fazerLogout(JsonObject req, Scanner scanner) {
        req.addProperty("op", "020");
        System.out.print("Usuário: ");
        req.addProperty("user", scanner.nextLine());
        System.out.print("Token: ");
        req.addProperty("token", scanner.nextLine());
    }

    private static void alterarCadastro(JsonObject req, Scanner scanner) {
        req.addProperty("op", "030");
        System.out.print("Usuário: ");
        req.addProperty("user", scanner.nextLine());
        System.out.print("Senha atual: ");
        req.addProperty("pass", scanner.nextLine());
        System.out.print("Novo apelido (ou vazio): ");
        req.addProperty("new_nick", scanner.nextLine());
        System.out.print("Nova senha (ou vazio): ");
        req.addProperty("new_pass", scanner.nextLine());
        System.out.print("Token: ");
        req.addProperty("token", scanner.nextLine());
    }

    private static void apagarCadastro(JsonObject req, Scanner scanner) {
        req.addProperty("op", "040");
        System.out.print("Usuário: ");
        req.addProperty("user", scanner.nextLine());
        System.out.print("Senha: ");
        req.addProperty("pass", scanner.nextLine());
        System.out.print("Token: ");
        req.addProperty("token", scanner.nextLine());
    }

    private static void buscarCadastro(JsonObject req, Scanner scanner) {
        req.addProperty("op", "005");
        System.out.print("Usuário: ");
        req.addProperty("user", scanner.nextLine());
        System.out.print("Token: ");
        req.addProperty("token", scanner.nextLine());
    }

    private static void alterarCadastroAdmin(JsonObject req, Scanner scanner) {
        req.addProperty("op", "080");
        System.out.print("Token: ");
        req.addProperty("token", scanner.nextLine());
        System.out.print("Usuário: ");
        req.addProperty("user", scanner.nextLine());
        System.out.print("Novo apelido (ou vazio): ");
        req.addProperty("new_nick", scanner.nextLine());
        System.out.print("Nova senha (ou vazio): ");
        req.addProperty("new_pass", scanner.nextLine());

    }

    private static void removerCadastroAdmin(JsonObject req, Scanner scanner) {
        req.addProperty("op", "090");
        System.out.print("Token: ");
        req.addProperty("token", scanner.nextLine());
        System.out.print("Usuário: ");
        req.addProperty("user", scanner.nextLine());
    }

    private static void buscarCadastradosAdmin(JsonObject req, Scanner scanner) {
        req.addProperty("op", "110");
        System.out.print("Token (ADMIN): ");
        req.addProperty("token", scanner.nextLine());
    }



}