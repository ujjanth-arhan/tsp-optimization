package org.info6205.tsp.algorithm;

import org.info6205.tsp.core.Edge;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.io.Preprocess;
import org.info6205.tsp.util.GraphUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;
import java.util.stream.IntStream;

public class ChristofidesAlgorithmTest {

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
    public void checkTSPTourHasExactNumberOfVerticesAsOriginalGraphPlusOne(){

        try {
            ChristofidesAlgorithm christofidesAlgorithm = new ChristofidesAlgorithm(testGraph);

            List<Vertex> tspTour = christofidesAlgorithm.generateTSPTour();

            Assertions.assertEquals(tspTour.size(), testGraph.getAllVertices().size()+1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void verifyTSPTourCostIsWithinDesiredRangeForChristofidesAlgorithm(){

        try {

            MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(testGraph);

            Graph mst = minimumSpanningTree.getMinimumSpanningTree();

            double mstCost = mst.getAllEdges().stream().mapToDouble(Edge::getWeight).reduce(0, (a,b)-> a+b)/2;

            ChristofidesAlgorithm christofidesAlgorithm = new ChristofidesAlgorithm(testGraph);

            double bestCost = Double.MAX_VALUE;
            List<Vertex> bestTour = null;

            for(int i=0; i<100; i++){
                List<Vertex> tspTour = christofidesAlgorithm.generateTSPTour();
                double currentCost = GraphUtil.getTotalCostOfTour(tspTour);
                if(bestCost > currentCost){
                    bestCost = currentCost;
                    bestTour = tspTour;
                }
            }

            System.out.println("Best cost of tour: " + bestCost);
            System.out.println("Best tour: " + bestTour);


            Assertions.assertTrue( bestCost >= mstCost * 1.5 && bestCost <= mstCost * 1.75);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
