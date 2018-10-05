import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private HashMap<Integer, String> wordnet_synsets;
    private HashMap<String, ArrayList<Integer>> wordnet_map;
    private HashMap<Integer, ArrayList<Integer>> wordnet_hypernyms;
    SAP sap;
    Digraph digraph;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        wordnet_synsets = new HashMap<Integer, String>();
        wordnet_hypernyms = new HashMap<Integer, ArrayList<Integer>>();
        wordnet_map = new HashMap<String, ArrayList<Integer>>();
        In synsetsInput = new In(synsets);
        In hypernymsInput = new In(hypernyms);

        while ((synsetsInput.hasNextLine())) {
            String currentLine = synsetsInput.readLine();
            int id = Integer.parseInt(currentLine.split(",")[0]);
            String nouns = currentLine.split(",")[1];
            wordnet_synsets.put(id, nouns);

            for (String noun : nouns.split(" ")) {
                if (wordnet_map.containsKey(noun)) {
                    wordnet_map.get(noun).add(id);
                } else {
                    ArrayList<Integer> ids = new ArrayList<Integer>();
                    ids.add(id);
                    wordnet_map.put(noun, ids);
                }
            }
        }
        digraph = new Digraph(wordnet_synsets.size());

        while ((hypernymsInput.hasNextLine())) {
            String currentLine = hypernymsInput.readLine();
            String[] lineSplit = currentLine.split(",");
            int synsetid = Integer.parseInt(lineSplit[0]);
            ArrayList<Integer> othersynsets = new ArrayList<Integer>();
            for (int i = 1; i < lineSplit.length; i++) {
                int other_id = Integer.parseInt(lineSplit[i]);
                othersynsets.add(other_id);
                digraph.addEdge(synsetid, other_id);
            }
            wordnet_hypernyms.put(synsetid, othersynsets);
        }

        sap = new SAP(digraph);


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
        for (String str : nouns()) {
            if (str.equals(word))
                return true;
        }
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {

        return sap.length(wordnet_map.get(nounA),  wordnet_map.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        ArrayList<Integer> nounsA_ID = wordnet_map.get(nounA);
        ArrayList<Integer> nounsB_ID = wordnet_map.get(nounB);
        int ancestor = sap.ancestor(nounsA_ID, nounsB_ID);
        return  wordnet_synsets.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        //System.out.println(((Collection<?>) wordNet.nouns()).size());
        //System.out.println(wordNet.isNoun("clockwork_universe"));
        System.out.println(wordNet.distance("worm", "bird"));
        System.out.println(wordNet.sap("worm", "bird"));
    }
}
