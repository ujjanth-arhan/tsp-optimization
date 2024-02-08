package org.info6205.tsp.algorithm;

import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.UndirectedSubGraph;
import org.info6205.tsp.io.Preprocess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.function.Function;

public class GreedyPerfectMatchingTest {

    static Graph testGraph;

    @BeforeAll
    public static void loadGraph(){

        try{
            Preprocess preprocess = new Preprocess();
            testGraph = preprocess.start("crimeSample.csv");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkNumberOfEdgesInPerfectMatching(){
        try {

            MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(testGraph);

            Graph mst = minimumSpanningTree.getMinimumSpanningTree();

            System.out.println("Number of odd degree vertices: " + mst.getOddDegreeVertices().size());

            Graph subGraph = new UndirectedSubGraph(mst.getOddDegreeVertices(), testGraph);
            GreedyPerfectMatching greedyPerfectMatching = new GreedyPerfectMatching(subGraph);

            Graph perfectMatching = greedyPerfectMatching.getPerfectMatching();

            System.out.println("Perfect matching:\n" + perfectMatching);

            //As edges are repeated twice in our UndirectedGraph implementation we are halving the total number of edges for testing
            Assertions.assertEquals(perfectMatching.getAllEdges().size()/2, mst.getOddDegreeVertices().size()/2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkPerfectMatchingCostIsAtMostHalfOfMSTCost(){
        try {

            MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(testGraph);

            Graph mst = minimumSpanningTree.getMinimumSpanningTree();

            Graph subGraph = new UndirectedSubGraph(mst.getOddDegreeVertices(), testGraph);
            GreedyPerfectMatching greedyPerfectMatching = new GreedyPerfectMatching(subGraph);

            Graph perfectMatching = greedyPerfectMatching.getPerfectMatching();

            Function<Graph, Double> getGraphCost = (graph) -> graph.getAllEdges().stream().mapToDouble(e -> e.getWeight()).reduce(0, (a,b)->a+b)/2;

            double mstCost = getGraphCost.apply(mst);
            double perfectMatchingCost = getGraphCost.apply(perfectMatching);

            System.out.println(mstCost/2+" " + perfectMatchingCost);
            //As edges are repeated twice in our UndirectedGraph implementation we are halving the total number of edges for testing
            Assertions.assertTrue(perfectMatchingCost < mstCost/2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
