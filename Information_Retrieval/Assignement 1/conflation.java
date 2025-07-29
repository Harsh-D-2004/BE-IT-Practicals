import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class conflation {

    static Set<String> stopWords = new HashSet<>(Arrays.asList("a", "an", "the", "is", "are", "was", "were", "to", "of", "i" , "and", "in", "on", "with" , "that", "for", "as", "at", "by", "from", "it", "or", "be", "this", "that", "have", "has", "had", "he", "she", "her", "his", "she", "they", "their", "we", "our", "what", "which", "who", "whom", "where", "when", "how", "all", "more", "most", "some", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just"));

    public static String readFile(String filename) throws IOException {

        String s;
        s = new String(Files.readAllBytes(new File(filename).toPath()));
        return s;
    }

    public static Map<String, Integer> conflationAlgo(String text) {

        Map<String, Integer> map = new HashMap<>();

        text = text.toLowerCase().replaceAll("[^a-z\\s]", " ");

        String[] words = text.split("\\s+");

        for(String word : words) {

            if(word.isEmpty() || stopWords.contains(word)) continue;

            word = simplePorterStem(word);

            if(map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

        return map;
    }

    public static void printMap (Map<String, Integer> map) {

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static String simplePorterStem(String word) {

        if (word.endsWith("ies")) {
            return word.substring(0, word.length() - 3) + "y";
        }

        if (word.endsWith("ing")) {
            return word.substring(0, word.length() - 3);
        }

        if (word.endsWith("ed")) {
            return word.substring(0, word.length() - 2);
        }

        if (word.endsWith("ful")) {
            return word.substring(0, word.length() - 3);
        }

        if (word.endsWith("ness")) {
            return word.substring(0, word.length() - 4);
        }

        if (word.endsWith("ly")) {
            return word.substring(0, word.length() - 2);
        }

        return word;
    }

    public static void main(String[] args) throws IOException {

        System.out.println("Output : ");

        String text = "I be doing that homework everyday so i can be successful";

        Map<String, Integer> map = conflationAlgo(text);
        printMap(map);

    }

}