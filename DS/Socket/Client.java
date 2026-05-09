import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 3300;

        try {
            Socket socket = new Socket(address, port);
            System.out.println("Connected to server!");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            BufferedReader console = new BufferedReader(
                    new InputStreamReader(System.in));

            // Thread to RECEIVE messages
            Thread receiveThread = new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Server: " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            // Thread to SEND messages
            Thread sendThread = new Thread(() -> {
                String msg;
                try {
                    while ((msg = console.readLine()) != null) {
                        out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            receiveThread.start();
            sendThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}