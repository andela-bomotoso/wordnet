import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }       // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        String outcast = "";
        int outcast_distance = 0;
        for (String i : nouns) {
            int curdist = 0;
            for (String j : nouns) {
                int dist = wordNet.distance(i, j);
                curdist += dist;
            }

            if (curdist > outcast_distance) {
                outcast_distance = curdist;
                outcast = i;
            }
        }


        return outcast;
    }// given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
