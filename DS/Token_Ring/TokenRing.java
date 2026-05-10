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

                while (ring.getTokenHolder() != id) {
                    try {
                        ring.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("\n--------------------------------");
                System.out.println("Token with Process " + id);
                System.out.println("Process " + id + " ENTERS critical section");

                for (int i = 0; i < ring.getN(); i++) {

                    if (i != id) {
                        System.out.println(
                            "Process " + i +
                            " wants to enter CS BUT token not available"
                        );
                    }
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Process " + id + " EXITS critical section");

                int next;

                do {
                    next = new Random().nextInt(ring.getN());
                } while (next == id);

                ring.setTokenHolder(next);

                System.out.println("Token passed to Process " + next);

                ring.notifyAll();
            }

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

        for (int i = 0; i < n; i++) {

            Process p = new Process(i, ring);
            p.start();
        }
    }
}