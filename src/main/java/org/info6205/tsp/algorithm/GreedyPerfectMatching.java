package org.info6205.tsp.algorithm;

import org.info6205.tsp.core.Edge;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.UndirectedGraph;
import org.info6205.tsp.core.Vertex;

import java.util.*;

/**
 * A greedy algorithm to get a perfect matching of a graph.
 * This is not the best possible implementation for perfect matching
 * but efficient implementations are out of scope for now
 */
public class GreedyPerfectMatching {

    /**
     * Graph for which perfect matching has to be generated
     */
    Graph graph;

    /**
     * Parameterized constructor for GreedyPerfectMatching
     * @param graph Initial graph for which perfect matching has to be generated
     */
    public GreedyPerfectMatching(Graph graph) {
        this.graph = graph;
    }

    /**
     * A greedy algorithm which sorts all the edges in the graph and
     * finds out the perfect matching by choosing edges with least weight
     * @return A graph containing a perfect matching (not minimum cost perfect matching)
     * @throws Exception
     */
    public Graph getPerfectMatching() throws Exception{
        Graph resGraph = new UndirectedGraph();
        Set<Vertex> vertices = graph.getAllVertices();

        if(vertices.size()%2 != 0)
            throw new Exception("Number of vertices is odd. Perfect matching not possible");
        try{
            for(Vertex v: graph.getAllVertices()){
                resGraph.addVertex(v);
            }
            List<Edge> edges = new ArrayList<>(graph.getAllEdges());
            Collections.sort(edges);
            Set<Vertex> visited = new HashSet<>();
            for (int i = 0; i < edges.size() && visited.size() < vertices.size(); i++) {
                Edge currEdge = edges.get(i);
                if(!visited.contains(currEdge.getSource()) && !visited.contains(currEdge.getDestination())){
                    visited.add(currEdge.getSource());
                    visited.add(currEdge.getDestination());
                    resGraph.addEdge(currEdge.getSource(), currEdge.getDestination());
                }
            }
            if(visited.size() != vertices.size())
                throw new Exception("Perfect matching not possible on this graph with current edge configuration");
            return resGraph;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
