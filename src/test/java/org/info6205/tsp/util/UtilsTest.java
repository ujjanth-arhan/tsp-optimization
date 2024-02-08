package org.info6205.tsp.util;

import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.UndirectedGraph;
import org.info6205.tsp.core.Vertex;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void testGenerateGraphFromEulerianCircuit() throws Exception {
       Graph graph= new UndirectedGraph();
            Vertex v1= new Vertex(1, 0, 0);
            Vertex v2= new Vertex(2,0, 0);
            Vertex v3= new Vertex(3, 0, 0);
            Vertex v4= new Vertex(4, 0, 0);
            Vertex v5= new Vertex(5, 0, 0);

            graph.addVertex(v1);
            graph.addVertex(v2);
            graph.addVertex(v3);
            graph.addVertex(v4);
            graph.addVertex(v5);

            List<Vertex> vertices = new ArrayList<>(Arrays.asList(v1, v2, v3, v4, v5));

            Graph g= GraphUtil.generateGraphFromEulerianCircuit(vertices);

            assertEquals(10, g.getAllEdges().size());
    }
}
