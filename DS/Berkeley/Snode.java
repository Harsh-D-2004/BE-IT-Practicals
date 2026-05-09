import java.io.*;
import java.net.*;
import java.util.*;

public class Snode {

    static final String HOST = "localhost";
    static final int PORT = 5000;

    // HH:mm → minutes
    static int toMinutes(String time) {
        String[] p = time.split(":");
        return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
    }

    // minutes → HH:mm
    static String toHHMM(int mins) {
        mins = (mins + 1440) % 1440;
        return String.format("%02d:%02d", mins / 60, mins % 60);
    }

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket(HOST, PORT);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter SLAVE time (HH:mm): ");
        String time = sc.nextLine();

        int localTime = toMinutes(time);

        System.out.println("Local time in minutes: " + localTime);

        // send to master
        dos.writeInt(localTime);

        // receive adjustment
        int adjustment = dis.readInt();

        int newTime = localTime + adjustment;

        System.out.println("Adjustment received: " + adjustment);
        System.out.println("Synchronized time: " + toHHMM(newTime));

        socket.close();
    }
}