package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Server avviato!");
        ServerSocket server = new ServerSocket(3000);

        do {
            Socket s = server.accept();
            System.out.println("Un client si è collegato!");

            ClientHandler t = new ClientHandler(s);
            t.start();
        } while (true);
    }
}