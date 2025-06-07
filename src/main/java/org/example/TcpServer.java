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
        System.out.println("Servidor na porta " + porta);

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
                try {
                    JsonObject json = JsonParser.parseString(input).getAsJsonObject();
                    String op = json.get("op").getAsString();

                    // Captura login e salva qual usuário usou o socket
                    if (op.equals("000")) {
                        String user = json.get("user").getAsString();
                        conexoes.put(clientSocket, user);
                    }

                    switch (op) {
                        case "000": Handlers.handleLogin(json, out); break;
                        case "010": Handlers.handleCadastro(json, out); break;
                        case "020": Handlers.handleRealizarLogout(json, out); break;
                        case "030": Handlers.handleAlterarCadastro(json, out); break;
                        case "040": Handlers.handleApagarCadastro(json, out); break;
                        case "050": Handlers.handleMensagemEnviar(json, out); break;
                        case "060": Handlers.handleMensagemReceber(json, out); break;
                        case "080": Handlers.handleAlterarUsuario(json, out); break;
                        case "090": Handlers.handleRemoverUsuario(json, out); break;
                        case "005": Handlers.handleBuscarCadastro(json, out); break;
                        default:
                            out.println(gson.toJson(new LoginResponseFailure("Operação desconhecida")));
                    }
                } catch (JsonSyntaxException | IllegalStateException e) {
                    out.println(gson.toJson(new LoginResponseFailure("JSON inválido ou mal formatado")));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na comunicação com o cliente.");
        } finally {
            // Quando o cliente se desconecta
            String usuario = conexoes.remove(clientSocket);
            if (usuario != null) {
                BancoUsuarios.atualizarToken(usuario, "");
                System.out.println("Token removido do usuário desconectado: " + usuario);
            }
        }
    }
}
