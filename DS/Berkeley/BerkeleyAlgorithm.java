import java.util.Scanner;

public class BerkeleyAlgorithm {

    // Convert HH:mm -> total minutes
    public static int toMinutes(String time) {

        String[] parts = time.split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        return hour * 60 + minute;
    }

    // Convert total minutes -> HH:mm
    public static String toHHMM(int totalMinutes) {

        // handle overflow/negative times
        totalMinutes = (totalMinutes + 1440) % 1440;

        int hour = totalMinutes / 60;
        int minute = totalMinutes % 60;

        return String.format("%02d:%02d", hour, minute);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of clients: ");
        int clients = sc.nextInt();
        sc.nextLine();

        int totalNodes = clients + 1;

        String[] inputTimes = new String[totalNodes];
        int[] clockTimes = new int[totalNodes];

        for (int i = 0; i < totalNodes; i++) {

            if (i == 0)
                System.out.print("Enter Server Time (HH:mm): ");
            else
                System.out.print("Enter Client " + i + " Time (HH:mm): ");

            inputTimes[i] = sc.nextLine();
            clockTimes[i] = toMinutes(inputTimes[i]);
        }

        System.out.println("\nBefore Synchronization:");

        for (int i = 0; i < totalNodes; i++) {

            if (i == 0)
                System.out.println("Server Clock  : " + inputTimes[i]);
            else
                System.out.println("Client " + i + " Clock : " + inputTimes[i]);
        }

        int total = 0;

        for (int i = 0; i < totalNodes; i++) {
            total += clockTimes[i];
        }

        int average = total / totalNodes;

        System.out.println("\nAverage Time: " + toHHMM(average));

        System.out.println("\nClock Adjustments:");

        for (int i = 0; i < totalNodes; i++) {

            int adjustment = average - clockTimes[i];

            if (i == 0)
                System.out.println(
                    "Server adjustment  : "
                    + adjustment
                    + " minutes"
                );
            else
                System.out.println(
                    "Client " + i
                    + " adjustment : "
                    + adjustment
                    + " minutes"
                );

            clockTimes[i] += adjustment;
        }

        System.out.println("\nAfter Synchronization:");

        for (int i = 0; i < totalNodes; i++) {

            if (i == 0)
                System.out.println(
                    "Server Clock  : "
                    + toHHMM(clockTimes[i])
                );
            else
                System.out.println(
                    "Client " + i
                    + " Clock : "
                    + toHHMM(clockTimes[i])
                );
        }

        sc.close();
    }
}