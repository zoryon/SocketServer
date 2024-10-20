package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.example.lib.ResponseMessages;

public class ClientHandler extends Thread {
    Socket s;

    public ClientHandler(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            int choice;
            do {
                choice = sanitize(in, out);

                switch (choice) {
                    case 0:
                        System.out.println("Connessione chiusa.");
                        break;
                    case 1:
                        sendRes(out, catchReq(in).toUpperCase());
                        break;
                    case 2:
                        sendRes(out, catchReq(in).toLowerCase());
                        break;
                    case 3:
                        sendRes(out, new StringBuilder(catchReq(in)).reverse().toString());
                        break;
                    case 4:
                        sendRes(out, Integer.toString(catchReq(in).length()));
                        break;
                    default:
                        sendRes(out, ResponseMessages.getDEFAULT_CHOICE());
                        break;
                }
            } while (choice != 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int sanitize(BufferedReader in, DataOutputStream out) throws IOException {
        String ans;
        boolean err = false;

        do {
            ans = in.readLine();

            try {
                Integer.parseInt(ans);
                out.writeBytes(ResponseMessages.getOk() + newLine());
                err = false;
            } catch (Exception e) {
                out.writeBytes(ResponseMessages.getINVALID_CHOICE() + newLine());
                err = true;
            }
        } while (err);

        return Integer.parseInt(ans);
    }

    public static String newLine() {
        return "\n";
    }

    public static String catchReq(BufferedReader in) throws IOException {
        return in.readLine();
    }

    public static void sendRes(DataOutputStream out, String ans) throws IOException {
        out.writeBytes(ans + newLine());
    }
}
