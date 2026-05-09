import java.util.Scanner;

public class Ring {

    Scanner sc;
    Process[] processes;
    int n;

    public Ring() {
        sc = new Scanner(System.in);
    }

    public void ring() {

        System.out.print("Enter total number of processes: ");
        n = sc.nextInt();

        processes = new Process[n];
        for (int i = 0; i < n; i++) {
            processes[i] = new Process(i);
        }
    }

    public void performElection() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Process " + processes[getMaxValue()].id + " fails");

        processes[getMaxValue()].status = "Inactive";

        int idOfInitiator = 0;
        boolean overStatus = true;

        while (overStatus) {
            System.out.println();
            int nextProcess = (idOfInitiator + 1) % n;

            if (processes[nextProcess].status == "active") {
                System.out.println("Process " + idOfInitiator + " passes Election(" + idOfInitiator + ") message to Process " + nextProcess);
            }

            if (processes[nextProcess].status == "active" && nextProcess == getMaxValue()) {
                System.out.println("Process " + nextProcess + " becomes the coordinator.");
                overStatus = false;
                break;
            }

            idOfInitiator = nextProcess;
        }
    }

    public int getMaxValue() {
        int mxId = -99;
        int mxIdIndex = 0;
        for (int i = 0; i < processes.length; i++) {
            if (processes[i].status == "active" && processes[i].id > mxId) {
                mxId = processes[i].id;
                mxIdIndex = i;
            }
        }
        return mxIdIndex;
    }

    public static void main(String[] args) {

    	Ring ring = new Ring();
        ring.ring();
        ring.performElection();
    }
}