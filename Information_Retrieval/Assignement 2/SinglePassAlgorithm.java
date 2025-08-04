
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class SinglePassAlgorithm {

    public static void main(String[] args) throws IOException {

        BufferedReader stdInpt = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter number of documents");
        int noOfDocs = Integer.parseInt(stdInpt.readLine());

        System.out.println("Enter number of tokens");
        int noOfTokens = Integer.parseInt(stdInpt.readLine());

        float threshold = 0.85f;

        System.out.println("Enter document token matrix");
        int[][] docMatrix = new int[noOfDocs][noOfTokens];

        for (int i = 0; i < noOfTokens; i++) {
            for (int j = 0; j < noOfTokens; j++) {
                System.out.println("Enter " + i + "th token of " + j + "th document");
                docMatrix[i][j] = Integer.parseInt(stdInpt.readLine());
            }
        }

        SinglePassAlgorithmDisjoint(noOfDocs, noOfTokens, docMatrix, threshold);

    }

    public static int[][] SinglePassAlgorithmDisjoint(int noOfDocs, int noOfTokens, int[][] docMatrix, float threshold) {

        int[][] cluster = new int[noOfDocs][noOfDocs + 1];
        ArrayList<Float[]> clusterRepresentative = new ArrayList<>();

        cluster[0][0] = 1;
        cluster[0][1] = 0;
        int noOfClusters = 1;

        Float[] firstDoc = convertToFloat(docMatrix[0]);
        clusterRepresentative.add(firstDoc);

        for (int i = 1; i < noOfDocs; i++) {
            float max = -1;
            int clusterId = -1;

            for (int j = 0; j < noOfClusters; j++) {
                float similarity = calculateCosineSimilarity(convertToFloat(docMatrix[i]), clusterRepresentative.get(j));

                if (similarity > threshold && similarity > max) {
                    max = similarity;
                    clusterId = j;
                }
            }

            if (max == -1) {
                cluster[noOfClusters][0] = 1;
                cluster[noOfClusters][1] = i;
                clusterRepresentative.add(convertToFloat(docMatrix[i]));
                noOfClusters++;
            } else {
                cluster[clusterId][0]++;
                int index = cluster[clusterId][0];
                cluster[clusterId][index] = i;

                clusterRepresentative.set(clusterId, calculateClusterRepresentative(cluster[clusterId], docMatrix, noOfTokens));
            }
        }

        for (int i = 0; i < noOfClusters; i++) {
            System.out.print("\nCluster " + i + ": ");
            for (int j = 1; j <= cluster[i][0]; ++j) {
                System.out.print("Doc" + cluster[i][j] + " ");
            }
        }

        return cluster;
    }

    // public static int[][] SinglePassAlgorithmOverlapping(int noOfDocs, int noOfTokens, int[][] docMatrix, float threshold) {
    //     List<List<Integer>> clusters = new ArrayList<>();
    //     List<Integer> representatives = new ArrayList<>(); // Stores index of representative document for each cluster

    //     // First cluster with doc 0
    //     List<Integer> firstCluster = new ArrayList<>();
    //     firstCluster.add(0);
    //     clusters.add(firstCluster);
    //     representatives.add(0);  // document 0 is rep of first cluster

    //     for (int i = 1; i < noOfDocs; i++) {
    //         boolean assigned = false;

    //         for (int j = 0; j < clusters.size(); j++) {
    //             int repDocIdx = representatives.get(j);
    //             float similarity = calculateCosineSimilarity(docMatrix, i, repDocIdx);

    //             if (similarity >= threshold) {
    //                 clusters.get(j).add(i);
    //                 assigned = true;

    //                 // Optional: recalculate new representative (e.g., centroid) — skipping for simplicity
    //             }
    //         }

    //         // If not added to any cluster, create a new cluster
    //         if (!assigned) {
    //             List<Integer> newCluster = new ArrayList<>();
    //             newCluster.add(i);
    //             clusters.add(newCluster);
    //             representatives.add(i);
    //         }
    //     }

    //     // Convert clusters to int[][]
    //     int[][] finalClusters = new int[clusters.size()][];
    //     for (int i = 0; i < clusters.size(); i++) {
    //         List<Integer> cluster = clusters.get(i);
    //         finalClusters[i] = new int[cluster.size()];
    //         for (int j = 0; j < cluster.size(); j++) {
    //             finalClusters[i][j] = cluster.get(j);
    //         }
    //     }

    //     return finalClusters;
    // }

    private static Float[] convertToFloat(int[] arr) {

        int size = arr.length;
        Float[] floatArr = new Float[size];

        for (int i = 0; i < size; i++) {
            floatArr[i] = (float) arr[i];
        }

        return floatArr;
    }

    private static Float calculateCosineSimilarity(Float[] vec1, Float[] vec2) {

        Float ans = 0f;

        for (int i = 0; i < vec1.length; i++) {
            ans += vec1[i] * vec2[i];
        }

        return ans;
    }

    private static Float[] calculateClusterRepresentative(int[] cluster, int[][] input, int noOFTokens) {
        Float[] answer = new Float[noOFTokens];
        for (int i = 0; i < noOFTokens; i++) {
            answer[i] = Float.valueOf("0");
        }

        for (int i = 1; i <= cluster[0]; ++i) {
            for (int j = 0; j < noOFTokens; j++) {
                answer[j] += input[cluster[i]][j];
            }
        }

        for (int i = 0; i < noOFTokens; i++) {
            answer[i] /= cluster[0];
        }
        return answer;
    }
}
