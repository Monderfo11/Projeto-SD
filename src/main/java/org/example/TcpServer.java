package org.example;

import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;

public class TcpServer extends Thread {
    private static Map<Socket, String> conexoes = new HashMap<>();
    private Socket clientSocket;

    public static void main(String[] args) throws IOException {
        System.out.print("Qual porta o servidor deve usar? ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int porta = Integer.parseInt(br.readLine());

        ServerSocket serverSocket = new ServerSocket(porta);
        System.out.println("Servidor iniciado na porta " + porta);

        while (true) {
            Socket socket = serverSocket.accept();
            new TcpServer(socket).start();
        }
    }

    public TcpServer(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        Gson gson = new Gson();

        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String input;
            while ((input = in.readLine()) != null) {
                System.out.println("Cliente → Servidor: " + input); // Log bruto da mensagem recebida

                try {
                    JsonObject json = JsonParser.parseString(input).getAsJsonObject();
                    String op = json.get("op").getAsString();
                    String user = json.has("user") ? json.get("user").getAsString() : "(desconhecido)";

                    switch (op) {
                        case "000":
                            Handlers.handleLogin(json, out);
                            System.out.println("Usuário " + user + " fez login.");
                            conexoes.put(clientSocket, user);
                            break;

                        case "010":
                            Handlers.handleCadastro(json, out);
                            System.out.println("Usuário " + user + " se cadastrou.");
                            break;

                        case "020":
                            Handlers.handleRealizarLogout(json, out);
                            System.out.println("⬅Usuário " + user + " fez logout.");
                            break;

                        case "030":
                            Handlers.handleAlterarCadastro(json, out);
                            System.out.println(" Usuário " + user + " alterou o cadastro.");
                            break;

                        case "040":
                            Handlers.handleApagarCadastro(json, out);
                            System.out.println(" Usuário " + user + " excluiu a conta.");
                            break;

                        case "050":
                            Handlers.handleMensagemEnviar(json, out);
                            System.out.println(" Usuário " + user + " enviou uma mensagem.");
                            break;

                        case "060":
                            Handlers.handleMensagemReceber(json, out);
                            System.out.println(" Usuário " + user + " leu uma mensagem.");
                            break;

                        case "080":
                            Handlers.handleAlterarUsuario(json, out);
                            System.out.println(" Admin alterou o usuário " + user + ".");
                            break;

                        case "090":
                            Handlers.handleRemoverUsuario(json, out);
                            System.out.println(" Admin removeu o usuário " + user + ".");
                            break;

                        case "005":
                            Handlers.handleBuscarCadastro(json, out);
                            System.out.println(" Usuário " + user + " buscou os dados do próprio cadastro.");
                            break;

                        case "095":
                            Handlers.handleBuscarLogados(json, out);
                            System.out.println(" Admin buscou usuários logados.");

                            break;


                        default:
                            String erro = gson.toJson(new LoginResponseFailure("Operação desconhecida"));
                            System.out.println(" Operação desconhecida recebida.");
                            out.println(erro);
                    }
                } catch (JsonSyntaxException | IllegalStateException e) {
                    String erro = gson.toJson(new LoginResponseFailure("JSON inválido ou mal formatado"));
                    System.out.println(" JSON mal formatado recebido.");
                    out.println(erro);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na comunicação com o cliente.");
        } finally {
            String usuario = conexoes.remove(clientSocket);
            if (usuario != null) {
                BancoUsuarios.atualizarToken(usuario, "");
                System.out.println(" Token removido do usuário desconectado: " + usuario);
            }
        }
    }
}
