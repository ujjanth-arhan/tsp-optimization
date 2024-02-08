package org.info6205.tsp.core;

import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;

public class EdgeTest {

    /**
     * Method to test the implementation of Euclidean distance between vertices
     */
    @Test
    public void testDistance(){
        Vertex v1= new Vertex(1, 42.360091919201636, -71.09408974968969);
        Vertex v2= new Vertex(2, 42.33988862950724, -71.09417558037691);
        Edge edge= new Edge(v1, v2);
        Assertions.assertEquals(edge.getWeight(), 2246.517481334295, 0.1);
    }
}
