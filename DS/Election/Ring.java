import java.util.Scanner;

class customProcess {

    int id;
    String status;

    customProcess(int id) {
        this.id = id;
        status = "active";
    }
}

public class Ring {

    Scanner sc;
    customProcess[] processes;
    int n;

    public Ring() {
        sc = new Scanner(System.in);
    }

    public void createProcesses() {

        System.out.print("Enter total number of processes: ");
        n = sc.nextInt();

        processes = new customProcess[n];

        for (int i = 0; i < n; i++) {
            processes[i] = new customProcess(i);
        }
    }

    public void performElection() {

        int failedProcess = getMaxProcess();

        System.out.println("\nProcess "
                + failedProcess
                + " fails");

        processes[failedProcess].status = "inactive";

        int initiator = 0;

        String message = "";

        int current = initiator;

        do {

            message += current + " ";

            int next = (current + 1) % n;

            while (processes[next].status.equals("inactive")) {
                next = (next + 1) % n;
            }

            System.out.println(
                    "\nProcess "
                    + current
                    + " passes Election("
                    + message.trim()
                    + ") to Process "
                    + next
            );

            current = next;

        } while (current != initiator);

        int coordinator = getMaxProcess();

        System.out.println(
                "\nProcess "
                + coordinator
                + " becomes the coordinator."
        );
    }

    public int getMaxProcess() {

        int maxId = -1;

        for (int i = 0; i < n; i++) {

            if (processes[i].status.equals("active")
                    && processes[i].id > maxId) {

                maxId = processes[i].id;
            }
        }

        return maxId;
    }

    public static void main(String[] args) {

        Ring ring = new Ring();

        ring.createProcesses();

        ring.performElection();
    }
}