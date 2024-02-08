package org.info6205.tsp.core;

import java.util.*;
import java.util.stream.Collectors;

public class UndirectedGraph implements Graph{

    /**
     * Internal data structure to hold graph
     * Contains a map of vertices and their corresponding edges
     */
    HashMap<Vertex, List<Edge>> graph;

    /**
     * Default constructor for UndirectedGraph
     * Initializes an empty graph
     */
    public UndirectedGraph(){
        graph = new HashMap<>();
    }

    /**
     * Adding vertex to the graph
     * @param vertex Vertex to be added
     * @return true if vertex is added. false if vertex is not added
     * @throws Exception
     */
    @Override
    public boolean addVertex(Vertex vertex) throws Exception{
        if(isVertexAlreadyPresent(vertex))
            throw new Exception(vertex + " already present in graph");
        return graph.put(vertex, new ArrayList<Edge>()) == null;
    }

    /**
     * Removing vertex from graph
     * @param vertex Vertex to be removed
     * @return list of adjacent edges of that vertex
     * @throws Exception
     */
    @Override
    public List<Edge> removeVertex(Vertex vertex) throws Exception{
        if(!isVertexAlreadyPresent(vertex))
            throw new Exception(vertex + " not present in graph");

        return graph.remove(vertex);
    }

    /**
     * Getting all vertices in the graph
     * @return vertices in the graph
     */
    @Override
    public Set<Vertex> getAllVertices() {
        return graph.keySet();
    }

    /**
     * Adds weighted edges to the graph. Weight is passed as a parameter
     *
     * @param sourceVertex Source vertex for the edge
     * @param destinationVertex Destination vertex for the edge
     * @param cost  Weight of the edge
     * @throws Exception Throws exception if source or destination vertices are not present
     */
    @Override
    public void addEdge(Vertex sourceVertex, Vertex destinationVertex, double cost) throws Exception{
        if(!isVertexAlreadyPresent(sourceVertex))
            throw new Exception(sourceVertex + " not present in graph");
        if(!isVertexAlreadyPresent(destinationVertex))
            throw new Exception(destinationVertex + " not present in graph");
        graph.get(sourceVertex).add(new Edge(sourceVertex, destinationVertex, cost));
        graph.get(destinationVertex).add(new Edge(destinationVertex, sourceVertex, cost));
    }

    /**
     * Adds weighted edges to the graph, weight is Euclidean distance between the source
     * and destination vertices which is calculated in Edge class
     *
     * @param sourceVertex Source vertex for the edge
     * @param destinationVertex Destination vertex for the edge
     * @throws Exception Throws exception if source or destination vertices are not present
     */
    @Override
    public void addEdge(Vertex sourceVertex, Vertex destinationVertex) throws Exception {
        if(!isVertexAlreadyPresent(sourceVertex))
            throw new Exception("sourceVertex not present in graph");
        if(!isVertexAlreadyPresent(destinationVertex))
            throw new Exception("destinationVertex not present in graph");
        graph.get(sourceVertex).add(new Edge(sourceVertex, destinationVertex));
        graph.get(destinationVertex).add(new Edge(destinationVertex, sourceVertex));
    }

    /**
     * Adding already existing edges to graph
     * @param edges Edges to be added to graph
     * @throws Exception
     */
    @Override
    public void addExistingEdgesToGraph(List<Edge> edges) throws Exception {
        for(Edge edge: edges){
            Vertex sourceVertex = edge.getSource();
            Vertex destinationVertex = edge.getDestination();
            if(!isVertexAlreadyPresent(sourceVertex))
                throw new Exception(sourceVertex + " not present in graph");
            if(!isVertexAlreadyPresent(destinationVertex))
                throw new Exception(destinationVertex + " not present in graph");
            graph.get(sourceVertex).add(edge);
        }
    }

    /**
     * Removing all edges between two given vertices
     * @param sourceVertex Source vertex of the edge
     * @param destinationVertex Destination vertex of the edge
     * @throws Exception
     */
    @Override
    public void removeAllEdgesBetweenVertices(Vertex sourceVertex, Vertex destinationVertex) throws Exception{
        if(!isVertexAlreadyPresent(sourceVertex))
            throw new Exception(sourceVertex + " not present in graph");
        if(!isVertexAlreadyPresent(destinationVertex))
            throw new Exception(destinationVertex + " not present in graph");

        graph.get(sourceVertex).removeAll(getEdgesBetweenVertices(sourceVertex, destinationVertex));
        graph.get(destinationVertex).removeAll(getEdgesBetweenVertices(destinationVertex, sourceVertex));
    }

    /**
     * Getting all adjacent edges of a particular vertex
     * @param vertex Vertex for which adjacent edges are needed
     * @return A set containing adjacent edges to the vertex
     * @throws Exception
     */
    @Override
    public Set<Edge> getAllAdjacentEdgesOfVertex(Vertex vertex) throws Exception{
        if(!isVertexAlreadyPresent(vertex))
            throw new Exception(vertex + " not present in graph");

        return new HashSet<>(graph.get(vertex));
    }

    /**
     * Getting all edges between two vertices
     * @param sourceVertex Source vertex of the edge
     * @param destinationVertex Destination vertex of the edge
     * @return A set containing all edges between vertices
     * @throws Exception
     */
    @Override
    public Set<Edge> getEdgesBetweenVertices(Vertex sourceVertex, Vertex destinationVertex) throws Exception{
        if(!isVertexAlreadyPresent(sourceVertex))
            throw new Exception(sourceVertex + " not present in graph");
        if(!isVertexAlreadyPresent(destinationVertex))
            throw new Exception(destinationVertex + " not present in graph");
        Set<Edge> edges = new HashSet<>();
        edges.addAll(graph.get(sourceVertex).stream().filter(e -> e.getDestination().equals(destinationVertex)).collect(Collectors.toSet()));
        edges.addAll(graph.get(destinationVertex).stream().filter(e -> e.getDestination().equals(sourceVertex)).collect(Collectors.toSet()));
        return edges;
    }

    /**
     * Get list of all edges in graph
     * @return A set containing all edges in the graph
     */
    @Override
    public Set<Edge> getAllEdges() {
        return graph.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    /**
     * Get list of odd degree vertices in graph
     * @return A set containing all odd degree vertices in graph
     */
    @Override
    public Set<Vertex> getOddDegreeVertices() {
        return graph.keySet().stream().filter(v -> graph.get(v).size() % 2 != 0).collect(Collectors.toSet());
    }

    /**
     * Checks if vertex is already present in graph
     * @param vertex Vertex to perform the check for
     * @return true if vertex is present else false
     */
    private boolean isVertexAlreadyPresent(Vertex vertex) {
        return graph.containsKey(vertex) || graph.keySet().stream().anyMatch(v -> v.equals(vertex));
    }

    /**
     * Overriding default to string
     * @return Custom string containing a list of edges in the graph
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Graph:\n");
        for(Edge edge: getAllEdges()){
            sb.append(edge.getSource().toString()+"---"+ edge.getWeight()+"--->"+edge.getDestination().toString()+"\n");
        }

        return sb.toString();
    }
}
