import java.util.*;

class Process extends Thread {

    private int id;
    private TokenRing ring;

    public Process(int id, TokenRing ring) {
        this.id = id;
        this.ring = ring;
    }

    public int getProcessId() {
        return id;
    }

    @Override
    public void run() {

        while (true) {

            synchronized (ring) {

                // Wait until this process gets token
                while (ring.getTokenHolder() != id) {
                    try {
                        ring.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Critical Section
                System.out.println("\n--------------------------------");
                System.out.println("Token with Process " + id);
                System.out.println("Process " + id + " ENTERS critical section");

                // Other processes trying
                for (int i = 0; i < ring.getN(); i++) {

                    if (i != id) {
                        System.out.println(
                            "Process " + i +
                            " wants to enter CS BUT token not available"
                        );
                    }
                }

                // Hold critical section
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Process " + id + " EXITS critical section");

                // Random token passing
                int next;

                do {
                    next = new Random().nextInt(ring.getN());
                } while (next == id);

                ring.setTokenHolder(next);

                System.out.println("Token passed to Process " + next);

                // Wake all waiting threads
                ring.notifyAll();
            }

            // Small delay outside synchronized block
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TokenRing {

    private int n;
    private int tokenHolder;

    public TokenRing(int n) {
        this.n = n;

        // Random initial token holder
        tokenHolder = new Random().nextInt(n);
    }

    public int getN() {
        return n;
    }

    public int getTokenHolder() {
        return tokenHolder;
    }

    public void setTokenHolder(int tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        TokenRing ring = new TokenRing(n);

        // Create processes (threads)
        for (int i = 0; i < n; i++) {

            Process p = new Process(i, ring);
            p.start();
        }
    }
}