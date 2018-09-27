import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private HashMap<Integer, String> wordnet_synsets;
    private HashMap<Integer, ArrayList<Integer>> wordnet_hypernyms;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        wordnet_synsets = new HashMap<Integer, String>();
        wordnet_hypernyms = new HashMap<Integer, ArrayList<Integer>>();
        In synsetsInput = new In(synsets);
        In hypernymsInput = new In(synsets);

        while ((synsetsInput.hasNextLine())) {
            String currentLine = synsetsInput.readLine();
            int id = Integer.parseInt(currentLine.split(",")[0]);
            String nouns = currentLine.split(",")[1];
            wordnet_synsets.put(id, nouns);
        }

        while ((hypernymsInput.hasNextLine())) {
            String currentLine = hypernymsInput.readLine();
            String[] lineSplit = currentLine.split(" ");
            int synsetid = Integer.parseInt(lineSplit[0]);
            ArrayList<Integer> othersynsets = new ArrayList<Integer>();
            for (int i = 1; i < lineSplit.length; i++) {
                othersynsets.add(Integer.parseInt(lineSplit[i]));
            }
            wordnet_hypernyms.put(synsetid, othersynsets);
        }

    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        HashSet<String> nouns = new HashSet<String>();
        for (String s : wordnet_synsets.values()) {
            for (String t : s.split(" ")) {
                nouns.add(t);
            }
        }
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        Iterable<String> iterable = nouns();
        for (String str : nouns()) {
            if (str.equals(word))
                return true;
        }
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        int nounA_ID = 0, nounB_ID = 0;
        for (Map.Entry entry : wordnet_synsets.entrySet()) {
            if (nounA.equals(entry.getValue())) {
                nounA_ID = (Integer) entry.getKey();
            } else if (nounB.equals(entry.getValue())) {
                nounB_ID = (Integer) entry.getKey();
            }
        }

        return Math.abs(nounA_ID - nounB_ID);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        // System.out.println(((Collection<?>) wordNet.nouns()).size());
        System.out.println(wordNet.isNoun("clockwork_universe"));
    }
}
