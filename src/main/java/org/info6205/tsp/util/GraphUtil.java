package org.info6205.tsp.util;

import org.apache.lucene.util.SloppyMath;
import org.info6205.tsp.core.Edge;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.UndirectedGraph;
import org.info6205.tsp.core.Vertex;

import java.util.*;

/**
 * Utility class for graphs
 */
public class GraphUtil {

    /**
     * Get total cost of tour from a list of vertices
     * @param tour Tour for which cost has to be found
     * @return total cost of the tour
     */
    public static double getTotalCostOfTour(List<Vertex> tour){
        double totalCost = 0;
        for (int i = 0; i < tour.size()-1; i++) {
            totalCost += getDistanceBetweenVertices(tour.get(i), tour.get(i+1));
        }
        return totalCost;
    }

    /**
     * Distance between vertices calculated using Haversine formula
     * @param v1 First vertex
     * @param v2 Second vertex
     * @return distance between vertices
     */
    public static double getDistanceBetweenVertices(Vertex v1, Vertex v2){
        return SloppyMath.haversinMeters(v1.getXPos(), v1.getYPos(), v2.getXPos(), v2.getYPos());
    }

    /**
     * Getting a TSP tour from an Euler tour
     * @param tour Euler tour
     * @return Final TSP tour after removing duplicates from original tour
     */
    public static List<Vertex> getTSPTour(List<Vertex> tour) {
        Map<Vertex, Integer> visited = new HashMap<>();
        List<Vertex> result = new ArrayList<>();
        for (Vertex vertex: tour) {
            if (visited.putIfAbsent(vertex, 1) == null) result.add(vertex);
        }

        result.add(result.get(0));
        return result;
    }

    /**
     * Removes duplicate edges from multi-graph for visualization library purposes
     * Removing duplicates here means to remove the edge from destination to source
     * if the source to destination edge is already included
     * @param graph The graph from which duplicates have to be removed
     * @return List of edges of the graph with the duplicates removed
     */
    public static List<Edge> removeDuplicateUndirectedEdgesFromMultigraph(Graph graph){

        List<Edge> edgeList = new ArrayList<>(graph.getAllEdges());
        edgeList.sort((a,b)-> {
            if(a.getSource() == b.getSource()){
                return Long.compare(a.getDestination().getId(), b.getDestination().getId());
            }
            else
                return Long.compare(a.getSource().getId(), b.getSource().getId());
        });
        Set<String> keysOfEdgesToRemove = new HashSet<>();
        Set<Edge> edgesToRemove = new HashSet<>();
        for(Edge edge: edgeList){
            if(keysOfEdgesToRemove.contains(edge.getSource().getId()+"s"+edge.getDestination().getId()+"d")){
                edgesToRemove.add(edge);
            }
            else{
                keysOfEdgesToRemove.add(edge.getDestination().getId()+"s"+edge.getSource().getId()+"d");
            }
        }
        edgeList.removeAll(edgesToRemove);
        return edgeList;
    }

    /**
     * Generates a graph object from a given tour
     * Used for visualization library purposes
     * @param vertices The initial tour
     * @return a new graph object generated from the tour
     * @throws Exception
     */
    public static Graph generateGraphFromEulerianCircuit(List<Vertex> vertices) throws Exception {
        Graph graph= new UndirectedGraph();
        for(Vertex v: vertices){
            boolean isAlreadyAdded= false;
            for(Vertex gv: graph.getAllVertices())
                if(v.getId() == gv.getId())
                    isAlreadyAdded = true;

            if(!isAlreadyAdded)
                graph.addVertex(v);
        }
        for(int i=0; i< vertices.size(); i++){
            if(i+1 <= vertices.size()-1){
                graph.addEdge(vertices.get(i), vertices.get(i+1));
            }
        }
        return graph;
    }

    public static String printTSPTour(List<Vertex> tspTour, Map<Long, String> nodeMap){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tspTour.size(); i++) {
            String id = nodeMap.get(tspTour.get(i).getId());
            String modifiedId = id.substring(id.length()-5);
            sb.append(modifiedId+"-->");
        }
        sb.delete(sb.length()-3, sb.length());
        return sb.toString();
    }
}
