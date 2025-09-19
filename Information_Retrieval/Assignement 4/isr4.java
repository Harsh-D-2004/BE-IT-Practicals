import java.text.DecimalFormat;
import java.util.*;

public class isr4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of relevant documents (|R|): ");
        int rCount = sc.nextInt();

        Set<String> relevantDocs = new HashSet<>();
        System.out.println("Enter the document IDs of relevant documents:");
        for (int i = 0; i < rCount; i++) {
            relevantDocs.add(sc.next());
        }

        System.out.print("Enter number of retrieved documents (|A|): ");
        int aCount = sc.nextInt();

        List<String> retrievedDocs = new ArrayList<>();
        System.out.println("Enter the document IDs of retrieved documents in order:");
        for (int i = 0; i < aCount; i++) {
            retrievedDocs.add(sc.next());
        }

        System.out.println("\nRetrieved Documents Statistics:");
        System.out.printf("%-50s %-6s %-6s %-12s %-10s\n", 
                          "Retrieved Set", "|Ra|", "|A|", "Precision(%)", "Recall(%)");
        System.out.println("--------------------------------------------------------------------------------");

        Set<String> partialRetrieved = new HashSet<>();
        int relevantRetrieved = 0;
        DecimalFormat df = new DecimalFormat("0.00");

        for (int i = 0; i < retrievedDocs.size(); i++) {
            String docId = retrievedDocs.get(i);
            partialRetrieved.add(docId);

            if (relevantDocs.contains(docId)) {
                relevantRetrieved++;
            }

            double precision = (double) relevantRetrieved / partialRetrieved.size() * 100;
            double recall = (double) relevantRetrieved / relevantDocs.size() * 100;

            String currentRetrieved = String.join(",", retrievedDocs.subList(0, i + 1));

            System.out.printf("%-50s %-6d %-6d %-12s %-10s\n",
                    currentRetrieved, relevantRetrieved, partialRetrieved.size(),
                    df.format(precision), df.format(recall));
        }

        sc.close();
    }
}
