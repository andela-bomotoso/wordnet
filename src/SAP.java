import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Arrays;

public class SAP {
    private Digraph digraph;
    private BreadthFirstDirectedPaths[] bfdp;
    private int common_ancestor;
    private int path_distance;
    int shortest_path;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {
        this.digraph = digraph;
        bfdp = new BreadthFirstDirectedPaths[digraph.V()];
        common_ancestor = -1;
        path_distance = 0;
        shortest_path = Integer.MAX_VALUE;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v, w);
        //walkThroughPath(v, w);
        calculatePath(v,w);
        return shortest_path;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        //walkThroughPath(v, w);
        calculatePath(v,w);
        return common_ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        //walkthroughManyPaths(v, w);
        return shortest_path;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        walkthroughManyPaths(v, w);
        return common_ancestor;
    }

    //loop through all the vertices and see if there is an ancestor between v and w
    private void walkThroughPath(int v, int w) {

        validateVertex(v, w);
        resetSAP();

        BreadthFirstDirectedPaths bfdpv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdpw = new BreadthFirstDirectedPaths(digraph, w);
        bfdp[w] = new BreadthFirstDirectedPaths(digraph, w);

        for (int i = 0; i < digraph.V(); i++) {
            if (bfdpv.hasPathTo(i) && bfdpw.hasPathTo(i)) {
                path_distance = bfdp[v].distTo(i) + bfdp[w].distTo(i);
            }
            if (path_distance < shortest_path && path_distance != 0) {
                shortest_path = path_distance;
                common_ancestor = i;
            }
        }

        if (shortest_path == Integer.MAX_VALUE) {
            shortest_path = -1;
            common_ancestor = -1;
        }
    }

    private void walkthroughManyPaths(Iterable<Integer> v, Iterable<Integer> w) {
        validateIterable(v, w);
        resetSAP();

        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph, w);


        for (int i = 0; i < digraph.V(); i++) {
            if (bfs.hasPathTo(i) && bfs1.hasPathTo(i))
                path_distance = bfs.distTo(i) + bfs1.distTo(i);

            if (path_distance < shortest_path && path_distance != 0) {
                shortest_path = path_distance;
                common_ancestor = i;
            }
        }

        if (shortest_path == Integer.MAX_VALUE) {
            shortest_path = -1;
            common_ancestor = -1;
        }
    }

    public void resetSAP() {
        common_ancestor = -1;
        path_distance = 0;
        shortest_path = Integer.MAX_VALUE;
    }

    private void validateVertex(int v, int w) {
        if (v < 0 || w < 0 || v > digraph.V() - 1 || w > digraph.V() - 1)
            throw new java.lang.IllegalArgumentException();
    }

    private void validateIterable(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        for (Integer i : v) {
            if (i == null)
                throw new IllegalArgumentException();
        }
        for (Integer i : w) {
            if (i == null)
                throw new IllegalArgumentException();
        }
    }

    private void calculatePath(int v, int w)
    {
        resetSAP();
        bfdp[v] = new BreadthFirstDirectedPaths(digraph, v);
        bfdp[w] = new BreadthFirstDirectedPaths(digraph, w);

        for(int i = 0; i < digraph.V(); i++)
        {
            if(bfdp[v].hasPathTo(i) && bfdp[w].hasPathTo(i))
            {

                int distance = bfdp[v].distTo(i) +  bfdp[w].distTo(i);
                if(distance < shortest_path)
                    shortest_path = distance;
                common_ancestor = i;
            }
        }

        if(shortest_path == Integer.MAX_VALUE)
        {
            shortest_path = - 1;
            common_ancestor = -1;
        }

    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
//        ArrayList<Integer> A = new ArrayList<Integer>(Arrays.asList(13, 23, 24));
//        ArrayList<Integer> B = new ArrayList<Integer>(Arrays.asList(6, 16, 17));
//        int length = sap.length(A, B);
//        int ancestor = sap.ancestor(A, B);
//        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}