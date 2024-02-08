package org.info6205.tsp.core;


import java.util.List;
import java.util.Set;

/**
 * Base graph interface
 */
public interface Graph {

    /**
     * Adding vertex to the graph
     * @param vertex Vertex to be added
     * @return true if vertex is added. false if vertex is not added
     * @throws Exception
     */
    public boolean addVertex(Vertex vertex) throws Exception;

    /**
     * Removing vertex from graph
     * @param vertex Vertex to be removed
     * @return list of adjacent edges of that vertex
     * @throws Exception
     */
    public List<Edge> removeVertex(Vertex vertex) throws Exception;

    /**
     * Getting all vertices in the graph
     * @return vertices in the graph
     */
    public Set<Vertex> getAllVertices();

    /**
     * Adds weighted edges to the graph. Weight is passed as a parameter
     *
     * @param sourceVertex Source vertex for the edge
     * @param destinationVertex Destination vertex for the edge
     * @param cost  Weight of the edge
     * @throws Exception Throws exception if source or destination vertices are not present
     */
    public void addEdge(Vertex sourceVertex, Vertex destinationVertex, double cost) throws Exception;

    /**
     * Adds weighted edges to the graph, weight is Euclidean distance between the source
     * and destination vertices which is calculated in Edge class
     *
     * @param sourceVertex Source vertex for the edge
     * @param destinationVertex Destination vertex for the edge
     * @throws Exception Throws exception if source or destination vertices are not present
     */
    public void addEdge(Vertex sourceVertex, Vertex destinationVertex) throws Exception;

    /**
     * Adding already existing edges to graph
     * @param edges Edges to be added to graph
     * @throws Exception
     */
    public void addExistingEdgesToGraph(List<Edge> edges) throws Exception;

    /**
     * Removing all edges between two given vertices
     * @param sourceVertex Source vertex of the edge
     * @param destinationVertex Destination vertex of the edge
     * @throws Exception
     */
    public void removeAllEdgesBetweenVertices(Vertex sourceVertex, Vertex destinationVertex) throws Exception;

    /**
     * Getting all adjacent edges of a particular vertex
     * @param vertex Vertex for which adjacent edges are needed
     * @return A set containing adjacent edges to the vertex
     * @throws Exception
     */
    public Set<Edge> getAllAdjacentEdgesOfVertex(Vertex vertex) throws Exception;

    /**
     * Getting all edges between two vertices
     * @param sourceVertex Source vertex of the edge
     * @param destinationVertex Destination vertex of the edge
     * @return A set containing all edges between vertices
     * @throws Exception
     */
    public Set<Edge> getEdgesBetweenVertices(Vertex sourceVertex, Vertex destinationVertex) throws Exception;

    /**
     * Get list of all edges in graph
     * @return A set containing all edges in the graph
     */
    public Set<Edge> getAllEdges();

    /**
     * Get list of odd degree vertices in graph
     * @return A set containing all odd degree vertices in graph
     */
    public Set<Vertex> getOddDegreeVertices();

}
