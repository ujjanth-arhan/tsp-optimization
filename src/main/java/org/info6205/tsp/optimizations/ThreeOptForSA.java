package org.info6205.tsp.optimizations;

import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.util.GraphUtil;

import java.util.ArrayList;
import java.util.List;

public class ThreeOptForSA {

    /**
     * Initial tour to be optimized
     */
    private List<Vertex> tour;

    /**
     * Size of the tour excluding the last node(i.e. source node)
     */
    private int n;

    /**
     * Parameterized constructor taking in a tour
     * @param tour Initial tour to be optimized
     */
    public ThreeOptForSA(List<Vertex> tour) {
        this.tour = new ArrayList<>(tour);
        this.n = tour.size() - 1;
    }

    public List<Vertex> getTour() {
        return tour;
    }

    public void setTour(List<Vertex> tour) {
        this.tour = tour;
    }

    /**
     * Iterates over all valid segments for the 3-opt swap
     * and finds an optimum solution
     *
     * @return returns the cost delta after the three swap
     */
    public double doThreeOpt(int i, int j, int k){
        Triple triple = new Triple(i, j, k);
        return reverseSegmentIfBetter(tour, triple);
    }

    /**
     * Checks all possible combinations of 3-opt swap for provided indices
     * @param tour Tour to be optimized
     * @param triple Indices
     * @return the cost after optimization
     */
    private double reverseSegmentIfBetter(List<Vertex> tour, Triple triple) {
        Vertex a = tour.get(triple.a), b = tour.get(triple.a+1), c = tour.get(triple.b), d = tour.get(triple.b+1), e = tour.get(triple.c), f = tour.get(triple.c+1);
        double d0 = GraphUtil.getDistanceBetweenVertices(a,b) + GraphUtil.getDistanceBetweenVertices(c,d) + GraphUtil.getDistanceBetweenVertices(e,f);
        double d1 = GraphUtil.getDistanceBetweenVertices(a,c) + GraphUtil.getDistanceBetweenVertices(b,d) + GraphUtil.getDistanceBetweenVertices(e,f);
        double d2 = GraphUtil.getDistanceBetweenVertices(a,b) + GraphUtil.getDistanceBetweenVertices(c,e) + GraphUtil.getDistanceBetweenVertices(d,f);
        double d3 = GraphUtil.getDistanceBetweenVertices(a,d) + GraphUtil.getDistanceBetweenVertices(e,b) + GraphUtil.getDistanceBetweenVertices(c,f);
        double d4 = GraphUtil.getDistanceBetweenVertices(f,b) + GraphUtil.getDistanceBetweenVertices(c,d) + GraphUtil.getDistanceBetweenVertices(e,a);

        if(d0 > d1){
            reverseTourBetweenIndices(tour, triple.a, triple.b);
            return -d0 + d1;
        }
        else if(d0 > d2){
            reverseTourBetweenIndices(tour, triple.b, triple.c);
            return -d0 + d2;
        }
        else if(d0 > d4){
            reverseTourBetweenIndices(tour, triple.a, triple.c);
            return  -d0 + d4;
        }
        else if(d0 > d3){
            List<Vertex> temp = new ArrayList<>();
            for (int i = triple.b+1; i <= triple.c; i++) {
                temp.add(tour.get(i));
            }
            for (int i = triple.a+1; i <= triple.b; i++) {
                temp.add(tour.get(i));
            }
            int j = 0;
            for (int i = triple.a+1; i <= triple.c; i++) {
                tour.set(i, temp.get(j));
                j++;
            }
            return -d0 + d3;
        }
        return 0;
    }


    /**
     * Helper function to perform the 3-opt swap
     * @param vertices The current list of vertices
     * @param i Start index (exclusive)
     * @param j End index (inclusive)
     */
    private void reverseTourBetweenIndices(List<Vertex> vertices, int i, int j){
        List<Vertex> temp = new ArrayList<>();
        for (int k = j; k > i; k--) {
            temp.add(vertices.get(k));
        }
        int c = 0;
        for (int k = i+1; k <= j; k++) {
            vertices.set(k, temp.get(c));
            c++;
        }
    }

    private class Triple{
        public int a;
        public int b;
        public int c;

        public Triple(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
}
