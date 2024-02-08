package org.info6205.tsp.algorithm;

import org.info6205.tsp.core.Edge;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.UndirectedGraph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.io.Preprocess;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;

public class MinimumSpanningTreeTest {

    @Test
    public void testSpanningTree(){
        try {
            Graph g= new UndirectedGraph();
            Vertex v1 = new Vertex(1, 1.0, 1.0);
            g.addVertex(v1);
            Vertex v2= new Vertex(2, 2.0, 2.0);
            g.addVertex(v2);
            Vertex v3= new Vertex(3, 3.0, 3.0);
            g.addVertex(v3);
            Vertex v4= new Vertex(4, 4.0, 4.0);
            g.addVertex(v4);
            Vertex v5= new Vertex(5, 5.0, 5.0);
            g.addVertex(v5);

            g.addEdge(v1, v2, 1);
            g.addEdge(v1, v3, 7);
            g.addEdge(v2, v3, 5);
            g.addEdge(v2, v5, 3);
            g.addEdge(v2, v4, 4);
            g.addEdge(v3, v5, 6);
            g.addEdge(v4, v5, 2);

            MinimumSpanningTree mstclass= new MinimumSpanningTree(g);
            Graph mst= mstclass.getMinimumSpanningTree();
            System.out.println(mst.toString());
            int noOfVertices= mst.getAllVertices().size();
            int noOfEdges= mst.getAllEdges().size();


            Assertions.assertEquals(8, noOfEdges);

            //As edges are double in our graph implementation
            Assertions.assertEquals(noOfEdges/2, noOfVertices-1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNumberOfEdgesInSpanningTreeAndCost() throws Exception {

        Graph graph = new Preprocess().start("teamprojectfinal.csv");

        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(graph);

        Graph mst = minimumSpanningTree.getMinimumSpanningTree();

        System.out.println("Cost of mst: " + mst.getAllEdges().stream().mapToDouble(Edge::getWeight).reduce(0, (a,b)->a+b)/2);

        Assertions.assertEquals(mst.getAllEdges().size()/2, mst.getAllVertices().size()-1);

    }
}
