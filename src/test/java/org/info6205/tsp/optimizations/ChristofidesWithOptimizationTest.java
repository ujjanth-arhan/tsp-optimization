package org.info6205.tsp.optimizations;


import org.info6205.tsp.algorithm.ChristofidesAlgorithm;
import org.info6205.tsp.algorithm.MinimumSpanningTree;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.io.Preprocess;
import org.info6205.tsp.optimizations.ThreeOptSwapOptimization;
import org.info6205.tsp.optimizations.TwoOptSwapOptimization;
import org.info6205.tsp.util.GraphUtil;
import org.junit.jupiter.api.*;

import java.util.List;

public class ChristofidesWithOptimizationTest {

    static Graph testGraph;

    @BeforeAll
    public static void loadGraph(){

        try{
            Preprocess preprocess = new Preprocess();
            testGraph = preprocess.start("teamprojectfinal.csv");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void CostAfterThreeOptSwapOptimizationWithinRangeOfMSTTest(){

        try {

            MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(testGraph);

            Graph mst = minimumSpanningTree.getMinimumSpanningTree();

            double mstCost = mst.getAllEdges().stream().mapToDouble(e -> e.getWeight()).reduce(0, (a,b)-> a+b)/2;

            ChristofidesAlgorithm christofidesAlgorithm = new ChristofidesAlgorithm(testGraph);

            double bestCostYet = Double.MAX_VALUE;

            for (int i = 0; i < 10; i++) {
                List<Vertex> tour = christofidesAlgorithm.generateTSPTour();

                ThreeOptSwapOptimization threeOptSwapOptimization = new ThreeOptSwapOptimization(tour);

                List<Vertex> optimizedTour = threeOptSwapOptimization.getOptimumTour();

                bestCostYet = Math.min(bestCostYet, GraphUtil.getTotalCostOfTour(optimizedTour));
            }

            System.out.println("Minimum spanning tree cost: " + mstCost);
            System.out.println("Minimum spanning tree cost: " + bestCostYet);

            //Checking if cost after optimization is not more than 25% of mst cost
            Assertions.assertEquals(mstCost, bestCostYet, mstCost/4);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void CostAfterTwoOptSwapOptimizationWithinRangeOfMSTTest(){

        try {

            MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(testGraph);

            Graph mst = minimumSpanningTree.getMinimumSpanningTree();

            double mstCost = mst.getAllEdges().stream().mapToDouble(e -> e.getWeight()).reduce(0, (a,b)-> a+b)/2;

            ChristofidesAlgorithm christofidesAlgorithm = new ChristofidesAlgorithm(testGraph);

            double bestCostYet = Double.MAX_VALUE;

            for (int i = 0; i < 10; i++) {
                List<Vertex> tour = christofidesAlgorithm.generateTSPTour();

                TwoOptSwapOptimization threeOptSwapOptimization = new TwoOptSwapOptimization(tour);

                List<Vertex> optimizedTour = threeOptSwapOptimization.getOptimumTour();

                bestCostYet = Math.min(bestCostYet, GraphUtil.getTotalCostOfTour(optimizedTour));
            }

            System.out.println("Minimum spanning tree cost: " + mstCost);
            System.out.println("Minimum spanning tree cost: " + bestCostYet);
            System.out.println("Difference: " + (bestCostYet - mstCost)/mstCost);

            //Checking if cost after optimization is not more than 25% of mst cost
            Assertions.assertEquals(mstCost, bestCostYet, mstCost/4);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
