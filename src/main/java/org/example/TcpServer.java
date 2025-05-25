package org.example;

import java.io.*;
import java.net.*;
import com.google.gson.*;

public class TcpServer extends Thread {
    protected Socket clientSocket;

    public static void main(String[] args) throws IOException {
        System.out.print("Qual porta o servidor deve usar? ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int porta = Integer.parseInt(br.readLine());

        System.out.println("Servidor carregado na porta " + porta);
        System.out.println("Aguardando conexao....\n ");

        ServerSocket serverSocket = new ServerSocket(porta);
        while (true) {
            Socket socket = serverSocket.accept();
            new TcpServer(socket);
            System.out.println("Conexao recebida.\n");
        }
    }

    private TcpServer(Socket clientSoc) {
        this.clientSocket = clientSoc;
        start();
    }

    @Override
    public void run() {
        System.out.println("Nova thread de comunicacao iniciada.\n");
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

                    switch (op) {
                        case "000":
                            Handlers.handleLogin(json, out);
                            break;
                        case "010":

                            Handlers.handleCadastro(json, out);
                            break;
                        case "020":
                            Handlers.handleRealizarLogout(json, out);
                            break;

                        case "030":
                            Handlers.handleAlterarCadastro(json, out);
                            break;
                        case "040":
                            Handlers.handleApagarCadastro(json, out);
                            break;
                        case "050":
                            Handlers.handleMensagemEnviar(json, out);
                            break;
                        case "060":
                            Handlers.handleMensagemReceber(json, out);




                        default:
                            out.println(gson.toJson(new LoginResponseFailure("Operação desconhecida")));
                    }
                } catch (JsonSyntaxException | IllegalStateException e) {
                    out.println(gson.toJson(new LoginResponseFailure("JSON inválido ou mal formatado")));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na comunicacao com o cliente.");
        }
    }
}