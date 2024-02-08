package org.info6205.tsp.algorithm;

import org.info6205.tsp.core.Edge;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.util.GraphUtil;

import java.util.*;

/**
 * Implementation of Hierholzer's algorithm to find euclidean circuit in a given connected undirected graph
 */
public class HierholzerEulerianCircuit {
    Graph graph;
    public HierholzerEulerianCircuit(Graph graph) {
        this.graph = graph;
    }

    List<Vertex> result = new ArrayList<>();
    Map<Edge, Integer> visited = new HashMap<>();

    /**
     * Get the Eulerian path of the given vertex circuit.
     * @return
     */
    public List<Vertex> getEulerianCircuit() {
        List<Vertex> vertices = new ArrayList<>(graph.getAllVertices());
        double minDistance = Double.MAX_VALUE;
        List<Vertex> optimalResult = null;
        for (int i = 0; i < vertices.size(); i++) {
            Vertex startVertex = vertices.get(i);
            dfs(startVertex);
            result.add(startVertex);
            visited = new HashMap<>();
            double distance = GraphUtil.getTotalCostOfTour(result);
            if (distance < minDistance) {
                minDistance = distance;
                optimalResult = new ArrayList<>(result);
            }

            result = new ArrayList<>();
        }

        return optimalResult;
    }

    /**
     * Depth first search implementation of starting from the vertex provided.
     * @param startVertex
     */
    private void dfs(Vertex startVertex) {
        result.add(startVertex);
        Set<Edge> edges;
        try {
            edges = graph.getAllAdjacentEdgesOfVertex(startVertex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Edge edge: edges) {
            if (visited.putIfAbsent(edge, 1) == null) dfs(edge.getDestination());
        }
    }
}
