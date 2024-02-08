package org.info6205.tsp.optimizations;

import org.info6205.tsp.algorithm.*;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.io.Preprocess;
import org.info6205.tsp.util.GraphUtil;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;

public class SimulatedAnnealingTest {

    @Test
    public void testWith3AOptimization() {
        System.out.println("*".repeat(5) + " Starting application " + "*".repeat(5));
        long startTime = System.nanoTime();
        Preprocess preprocess = new Preprocess();

        try {
            Graph graph = preprocess.start("crimeSample.csv");

            MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(graph);

            Graph mst = minimumSpanningTree.getMinimumSpanningTree();

            double mstCost = getCostOfGraph(mst);

            ChristofidesAlgorithm christofidesAlgorithm = new ChristofidesAlgorithm(graph);

            List<Vertex> bestTourYet = null;
            double bestCostYet = Double.MAX_VALUE;
            for (int i = 0; i < 10; i++) {
                List<Vertex> tspTour = christofidesAlgorithm.generateTSPTour();
                SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(tspTour, 100000, 51200, 0.9);
                List<Vertex> optimizedSATour = simulatedAnnealing.optimize();
                double saTourCost = GraphUtil.getTotalCostOfTour(optimizedSATour);
                if(bestCostYet > saTourCost){
                    bestTourYet = optimizedSATour;
                    bestCostYet = GraphUtil.getTotalCostOfTour(optimizedSATour);
                }
            }

            for(Vertex v: bestTourYet) System.out.print(v+"-->");
            System.out.println();
            System.out.println("Total cost of tour: " + bestCostYet);
            System.out.println(new HashSet<Vertex>(bestTourYet).size());

            Assertions.assertEquals(bestCostYet, mstCost, mstCost/4);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        System.out.println("*".repeat(5) + " Application has completed running " + "*".repeat(5));
        System.out.println("Running time: " + (endTime-startTime)/Math.pow(10,9));
    }

    private double getCostOfGraph(Graph graph){
        return graph.getAllEdges().stream().mapToDouble(e -> e.getWeight()).reduce(0, (a,b)-> a+b)/2;
    }
}
