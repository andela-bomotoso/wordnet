import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collection;


public class WordNet {
    private HashMap<Integer, String> wordnet_synsets;
    private HashMap<String, ArrayList<Integer>> wordnet_map;
    private HashMap<Integer, ArrayList<Integer>> wordnet_hypernyms;
    private SAP sap;
    private Digraph digraph;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if(synsets == null || hypernyms == null)
            throw new java.lang.IllegalArgumentException();

        wordnet_synsets = new HashMap<Integer, String>();
        wordnet_hypernyms = new HashMap<Integer, ArrayList<Integer>>();
        wordnet_map = new HashMap<String, ArrayList<Integer>>();

        HashSet<Integer> synsetsHypernyms = new HashSet<Integer>();

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
            synsetsHypernyms.add(synsetid);
            ArrayList<Integer> othersynsets = new ArrayList<Integer>();
            for (int i = 1; i < lineSplit.length; i++) {
                int other_id = Integer.parseInt(lineSplit[i]);
                othersynsets.add(other_id);
                digraph.addEdge(synsetid, other_id);
            }
            wordnet_hypernyms.put(synsetid, othersynsets);
        }

        //check that input is a rooted DAG

        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if(directedCycle.hasCycle() || wordnet_synsets.size() - synsetsHypernyms.size() > 1)
            throw new java.lang.IllegalArgumentException();
            sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
          return Collections.unmodifiableSet(wordnet_map.keySet());
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if(word == null)
            throw new java.lang.IllegalArgumentException();
        return wordnet_map.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateNoun(nounA, nounB);
        return sap.length(wordnet_map.get(nounA),  wordnet_map.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validateNoun(nounA, nounB);
        ArrayList<Integer> nounsA_ID = wordnet_map.get(nounA);
        ArrayList<Integer> nounsB_ID = wordnet_map.get(nounB);
        int ancestor = sap.ancestor(nounsA_ID, nounsB_ID);
        return  wordnet_synsets.get(ancestor);
    }

    private void validateNoun(String nounA, String nounB) {
        if(nounA == null || nounB == null)
            throw new java.lang.IllegalArgumentException();

        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(((Collection<?>) wordNet.nouns()).size());
        System.out.println(wordNet.distance("renal_disorder", "first_class"));
        //System.out.println(wordNet.sap("worm", "bird"));
    }
}