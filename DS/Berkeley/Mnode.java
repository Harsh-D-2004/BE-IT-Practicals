import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Mnode {

    static final int PORT = 5000;
    static final int NUM_SLAVES = 3;

    static int toMinutes(String time) {
        String[] p = time.split(":");
        return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
    }

    static String toHHMM(int mins) {
        mins = (mins + 1440) % 1440;
        return String.format("%02d:%02d", mins / 60, mins % 60);
    }

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(PORT);

        DataInputStream[] dis = new DataInputStream[NUM_SLAVES];
        DataOutputStream[] dos = new DataOutputStream[NUM_SLAVES];

        int[] times = new int[NUM_SLAVES + 1]; // +1 master

        System.out.println("Master started...");

        Scanner sc = new Scanner(System.in);

        // ---------------- MASTER CLOCK ----------------
        System.out.print("Enter MASTER time (HH:mm): ");
        String masterTime = sc.nextLine();
        times[0] = toMinutes(masterTime);

        System.out.println("Master time in minutes: " + times[0]);

        for (int i = 0; i < NUM_SLAVES; i++) {

            Socket s = server.accept();

            dis[i] = new DataInputStream(s.getInputStream());
            dos[i] = new DataOutputStream(s.getOutputStream());

            times[i + 1] = dis[i].readInt();

            System.out.println("Slave " + i + " time: " + toHHMM(times[i + 1]));
        }

        int sum = 0;
        for (int t : times) sum += t;

        int avg = sum / times.length;

        System.out.println("\nAverage time: " + toHHMM(avg));


        System.out.println("\nAdjustments:");

        System.out.println("Master adjustment: " + (avg - times[0]));

        for (int i = 0; i < NUM_SLAVES; i++) {

            int diff = avg - times[i + 1];

            System.out.println("Slave " + i + " adjustment: " + diff);

            dos[i].writeInt(diff);
        }

        server.close();
    }
}