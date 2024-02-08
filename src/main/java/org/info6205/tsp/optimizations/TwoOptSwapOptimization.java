package org.info6205.tsp.optimizations;

import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.util.GraphUtil;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class TwoOptSwapOptimization {

    /**
     * Tour for which optimization has to be done
     */
    private List<Vertex> tour;

    /**
     * Current cost of travelling the best tour
     */
    private double currentCost;

    /**
     * Size of the tour excluding the source vertex in the end
     */
    private int n;

    /**
     * Parameterized constructor for TwoOptSwapOptimization
     * @param tour Initial tour for which optimization has to be found
     */
    public TwoOptSwapOptimization(List<Vertex> tour) {
        this.tour = tour;
        this.currentCost = GraphUtil.getTotalCostOfTour(tour);
        this.n = tour.size() - 1;
    }


    /**
     * Runs optimization on the current tour
     * @return Optimized tour containing list of vertices
     */
    public List<Vertex> getOptimumTour(){

        optimize();
        return new ArrayList<>(tour);
    }

    /**
     * Function to perform the optimization.
     * Iterates over the list of vertices excluding the last and first vertex as source and destination should be same
     * Performs two opt swap if the overall cost decreases
     */
    private void optimize(){

        boolean improvementFound = true;
        while(improvementFound){
            improvementFound = false;
            for (int i = 0; i < n-2; i++) {
                for (int j = i+2; j < n; j++) {
                    Vertex v1 = tour.get(i);
                    Vertex v2 = tour.get(i+1);
                    Vertex v3 = tour.get(j);
                    Vertex v4 = tour.get(j+1);
                    double costDelta = - GraphUtil.getDistanceBetweenVertices(v1,v2) - GraphUtil.getDistanceBetweenVertices(v3,v4)
                            + GraphUtil.getDistanceBetweenVertices(v1,v3) + GraphUtil.getDistanceBetweenVertices(v2,v4);
                    if(costDelta < -1){
                        doTwoOptSwap(tour,i,j);
                        currentCost += costDelta;
                        improvementFound = true;
                    }
                }
            }
        }
    }

    /**
     * Reverses the tour from i+1 to j (both inclusive)
     * @param vertices Tour on which swap has to be performed
     * @param i Start index minus 1
     * @param j End index
     */
    private void doTwoOptSwap(List<Vertex> vertices, int i, int j){
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
}
