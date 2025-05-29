package org.example;

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
            System.out.println("Conectado. Digite mensagens JSON (ou 'bye' para sair):");

            while (true) {
                System.out.print(">>> ");
                String entrada = scanner.nextLine();

                if (entrada.equalsIgnoreCase("bye")) break;

                out.println(entrada);

                String resposta = in.readLine();
                System.out.println("Resposta: " + resposta);
            }

        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
