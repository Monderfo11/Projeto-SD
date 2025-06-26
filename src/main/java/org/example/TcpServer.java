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
                System.out.println("Cliente → Servidor: " + input);

                try {
                    JsonObject json = JsonParser.parseString(input).getAsJsonObject();
                    String op = json.get("op").getAsString();
                    String user = json.has("user") ? json.get("user").getAsString() : "(desconhecido)";

                    // Wrapper para interceptar saída do handler
                    StringWriter sw = new StringWriter();
                    PrintWriter tempOut = new PrintWriter(sw, true);

                    switch (op) {
                        case "000":
                            Handlers.handleLogin(json, tempOut);

                            conexoes.put(clientSocket, user);
                            break;
                        case "010":
                            Handlers.handleCadastro(json, tempOut);

                            break;
                        case "020":
                            Handlers.handleRealizarLogout(json, tempOut);

                            break;
                        case "030":
                            Handlers.handleAlterarCadastro(json, tempOut);

                            break;
                        case "040":
                            Handlers.handleApagarCadastro(json, tempOut);

                            break;
                        case "050":
                            Handlers.handleMensagemEnviar(json, tempOut);

                            break;
                        case "060":
                            Handlers.handleMensagemReceber(json, tempOut);

                            break;
                        case "080":
                            Handlers.handleAlterarUsuario(json, tempOut);

                            break;
                        case "090":
                            Handlers.handleRemoverUsuario(json, tempOut);

                            break;
                        case "005":
                            Handlers.handleBuscarCadastro(json, tempOut);

                            break;
                        case "110":
                            Handlers.handleListarTodosUsuarios(json, tempOut);

                            break;
                        default:
                            JsonObject erro = new JsonObject();
                            erro.addProperty("op", "998");
                            erro.addProperty("msg", "Código de operação inválido");
                            System.out.println(" Operação inválida recebida: " + op);
                            out.println(erro.toString());

                    }

                    // Envia resposta ao cliente
                    String resposta = sw.toString().trim();
                    out.println(resposta);
                    System.out.println("Servidor → Cliente: " + resposta);

                } catch (JsonSyntaxException | IllegalStateException e) {
                    JsonObject erro = new JsonObject();
                    erro.addProperty("op", "999");
                    erro.addProperty("msg", "JSON inválido ou mal formatado");

                    String resposta = erro.toString();
                    out.println(resposta);
                    System.out.println(" Servidor → Cliente: " + resposta);
                }
            }
        } catch (IOException e) {
            System.err.println(" Erro na comunicação com o cliente.");
        } finally {
            String usuario = conexoes.remove(clientSocket);
            if (usuario != null) {
                BancoUsuarios.atualizarToken(usuario, "");
                System.out.println("Token removido do usuário desconectado: " + usuario);
            }
        }
    }
}
