package org.example;

import java.io.*;
import java.net.*;
import com.google.gson.Gson;
import java.util.Scanner;

public class TcpClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Qual o IP do servidor? ");
        String serverIP = scanner.nextLine();

        System.out.print("Qual a Porta do servidor? ");
        int serverPort = Integer.parseInt(scanner.nextLine());

        System.out.println("Tentando conectar com host " + serverIP + " na porta " + serverPort);

        try (
                Socket socket = new Socket(serverIP, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            Gson gson = new Gson();

            while (true) {
                System.out.println("\n--- MENU ---");
                System.out.println("1 - Cadastrar");
                System.out.println("2 - Login");
                System.out.println("3 - Alterar Cadastro");

                System.out.println("4 - Apagar Cadastro");
                System.out.println("5 - Logout");

                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                String opcao = scanner.nextLine();

                if (opcao.equals("0")) {
                    System.out.println("Encerrando cliente.");
                    break;
                }

                switch (opcao) {
                    case "1" -> {
                        System.out.print("Usuário: ");
                        String user = scanner.nextLine();
                        System.out.print("Apelido: ");
                        String nick = scanner.nextLine();
                        System.out.print("Senha: ");
                        String pass = scanner.nextLine();

                        String json = String.format(
                                "{\"op\":\"010\",\"user\":\"%s\",\"nick\":\"%s\",\"pass\":\"%s\"}",
                                user, nick, pass
                        );


                        out.println(json);
                        String resposta = in.readLine();
                        System.out.println("Resposta do servidor: " + resposta);
                    }

                    case "2" -> {
                        System.out.print("Usuário: ");
                        String user = scanner.nextLine();
                        System.out.print("Senha: ");
                        String pass = scanner.nextLine();

                        String json = String.format(
                                "{\"op\":\"000\",\"user\":\"%s\",\"pass\":\"%s\"}",
                                user, pass
                        );


                        out.println(json);
                        String resposta = in.readLine();
                        System.out.println("Resposta do servidor: " + resposta);
                    }
                    case "3" -> {
                        System.out.print("Usuário: ");
                        String user = scanner.nextLine();
                        System.out.print("Senha atual: ");
                        String pass = scanner.nextLine();
                        System.out.print("Novo apelido (pressione ENTER para manter): ");
                        String newNick = scanner.nextLine();
                        System.out.print("Nova senha (pressione ENTER para manter): ");
                        String newPass = scanner.nextLine();

                        String json = String.format(
                                "{\"op\":\"030\",\"user\":\"%s\",\"pass\":\"%s\",\"new_nick\":\"%s\",\"new_pass\":\"%s\"}",
                                user, pass, newNick, newPass
                        );

                        out.println(json);
                        String resposta = in.readLine();
                        System.out.println("Resposta do servidor: " + resposta);
                    }
                    case "4" -> {
                        System.out.print("Usuário: ");
                        String user = scanner.nextLine();
                        System.out.print("Senha: ");
                        String pass = scanner.nextLine();

                        String json = String.format("{\"op\":\"040\",\"user\":\"%s\",\"pass\":\"%s\"}", user, pass);
                        out.println(json);
                        String resposta = in.readLine();
                        System.out.println("Resposta do servidor: " + resposta);
                    }

                     case "5" -> {
                        System.out.print("Usuário: ");
                        String user = scanner.nextLine();
                        System.out.print("Token: ");
                        String token = scanner.nextLine();

                        String json = String.format("{\"op\":\"020\",\"user\":\"%s\",\"token\":\"%s\"}", user, token);
                        out.println(json);
                        String resposta = in.readLine();
                        System.out.println("Resposta do servidor: " + resposta);

                   }


                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
    }
}
