package org.info6205.tsp.core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An UndirectedGraph implementation that takes in a list of vertices
 * and constructs a subgraph by extracting the edges from the original
 * graph for the given vertices
 */
public class UndirectedSubGraph extends UndirectedGraph{

    /**
     * Parameterized constructor for UndirectedSubGraph
     * @param vertices List of vertices to be included in the new graph
     * @param graph Original graph
     */
    public UndirectedSubGraph(Set<Vertex> vertices, Graph graph){
        this.graph = new HashMap<>();
        vertices.forEach(v -> this.graph.put(v,new ArrayList<>()));
        try{
            for (Vertex v : vertices) {
                for(Edge edge: graph.getAllAdjacentEdgesOfVertex(v)){
                    if(vertices.contains(edge.getDestination()))
                        this.graph.get(v).add(edge);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Default constructor is hidden
     */
    private UndirectedSubGraph() {}

}
