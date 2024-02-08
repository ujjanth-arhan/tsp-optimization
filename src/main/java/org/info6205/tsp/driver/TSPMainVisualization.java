package org.info6205.tsp.driver;

import org.info6205.tsp.algorithm.ChristofidesAlgorithm;
import org.info6205.tsp.algorithm.ChristofidesAlgorithmVisualization;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.io.Preprocess;
import org.info6205.tsp.optimizations.SimulatedAnnealing;
import org.info6205.tsp.optimizations.ThreeOptSwapOptimization;
import org.info6205.tsp.util.GraphUtil;

import java.util.HashSet;
import java.util.List;

public class TSPMainVisualization {
    public static void main( String[] args )
    {

        System.out.println("*".repeat(5) + " Starting application " + "*".repeat(5));

        long startTime = System.nanoTime();
        Preprocess preprocess = new Preprocess();
        Graph graph = null;
        try {
            graph = preprocess.start("teamprojectfinal.csv");

            ChristofidesAlgorithmVisualization christofidesAlgorithm = new ChristofidesAlgorithmVisualization(graph);

            List<Vertex> bestTourYet = null;
            double bestCostYet = Double.MAX_VALUE;
            for (int i = 0; i < 1; i++) {

                List<Vertex> tspTour = christofidesAlgorithm.generateTSPTour();

                SimulatedAnnealing sa= new SimulatedAnnealing(tspTour, 51200000, 3200, 0.5);
                List<Vertex> saTour= sa.optimize();


                double saTourCost = GraphUtil.getTotalCostOfTour(saTour);
                System.out.println("Simulated Annealing TSP tour cost: " + saTourCost);


                if(bestCostYet > GraphUtil.getTotalCostOfTour(saTour)){
                    bestTourYet = saTour;
                    bestCostYet = GraphUtil.getTotalCostOfTour(saTour);
                }
            }

            for(Vertex v: bestTourYet) System.out.print(v+"-->");
            System.out.println();
            System.out.println("Total cost of tour: " + bestCostYet);
            System.out.println(new HashSet<Vertex>(bestTourYet).size());

        }
        catch (Exception e){
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        System.out.println("*".repeat(5) + " Application has completed running " + "*".repeat(5));
        System.out.println("Running time: " + (endTime-startTime)/Math.pow(10,9));
    }
}
