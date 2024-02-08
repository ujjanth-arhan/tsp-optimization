package org.info6205.tsp.driver;

import org.info6205.tsp.algorithm.*;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.UndirectedGraph;
import org.info6205.tsp.core.UndirectedSubGraph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.io.PostProcess;
import org.info6205.tsp.io.Preprocess;
import org.info6205.tsp.optimizations.ThreeOptSwapOptimization;
import org.info6205.tsp.util.GraphUtil;

import java.util.*;

/**
 * Hello world!
 *
 */
public class TSPMain
{
    public static void main( String[] args )
    {

        System.out.println("*".repeat(5) + " Starting application " + "*".repeat(5));

        long startTime = System.nanoTime();
        Preprocess preprocess = new Preprocess();
        Graph graph = null;
        try {
            graph = preprocess.start("teamprojectfinal.csv");
            ChristofidesAlgorithm christofidesAlgorithm = new ChristofidesAlgorithm(graph);

            List<Vertex> bestTourYet = null;
            double bestCostYet = Double.MAX_VALUE;
            for (int i = 0; i < 20; i++) {

                List<Vertex> tspTour = christofidesAlgorithm.generateTSPTour();

                double tspTourCost = GraphUtil.getTotalCostOfTour(tspTour);

                if(bestCostYet > tspTourCost){
                    bestTourYet = tspTour;
                    bestCostYet = tspTourCost;
                }
            }

            System.out.println(GraphUtil.printTSPTour(bestTourYet, preprocess.getNodeMap()));
            System.out.println("Total cost of tour: " + bestCostYet);

            PostProcess postProcess = new PostProcess(preprocess);

            postProcess.start(bestTourYet, "christofidesOutput.csv");

        }
        catch (Exception e){
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        System.out.println("*".repeat(5) + " Application has completed running " + "*".repeat(5));
        System.out.println("Running time: " + (endTime-startTime)/Math.pow(10,9));
    }

}
