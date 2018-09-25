import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class WordNet {
    private HashMap<Integer, String> wordnet_nouns;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        wordnet_nouns = new HashMap<Integer, String>();
        In synsetsInput = new In(synsets);
        while ((synsetsInput.hasNextLine())) {
            String currentLine = synsetsInput.readLine();
            int id = Integer.parseInt(currentLine.split(",")[0]);
            String noun = currentLine.split(",")[1];
            wordnet_nouns.put(id, noun);
        }

        In hypernymInput = new In(hypernyms);



    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        HashSet<String> nouns = new HashSet<String>();
        for (String noun : wordnet_nouns.values()) {
            String cur_noun[] = noun.split(" ");
            for (String str : cur_noun)
                nouns.add(str);
        }
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return wordnet_nouns.values().contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        int nounA_ID = 0, nounB_ID = 0;
        for(Map.Entry entry: wordnet_nouns.entrySet()){
            if(nounA.equals(entry.getValue())){
                nounA_ID = (Integer)entry.getKey();
                 //breaking because its one to one map
            }else if(nounB.equals(entry.getValue())){
                nounB_ID = (Integer)entry.getKey();
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

    }
}
