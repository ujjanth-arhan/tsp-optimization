package org.info6205.tsp.algorithm;

import org.info6205.tsp.core.Edge;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.UndirectedGraph;
import org.info6205.tsp.core.Vertex;

import java.util.*;

public class MinimumSpanningTree {
    /**
     * Boolean Map to keep track of visited nodes
     */
    private Map<Vertex, Boolean> visited;

    /**
     * Priority queue to get the edge with the minimum weight
     */
    private PriorityQueue<Edge> pq;

    /**
     * Undirected graph which is used to store the Minimum Spanning Tree
     */
    private Graph mst;

    /**
     * Graph object for which MST needs to be generated
     */
    private Graph graph;

    /**
     * Start vertex for the MST
     */
    private Vertex start = null;

    /**
     * Constructor to initialize the MST class with the Graph object
     * @param graph Graph object for which MST needs to be generated
     */
    public MinimumSpanningTree(Graph graph) throws Exception {
        //Initialize the graph object
        this.graph = graph;
        //Initialize the pq, mst and visited boolean map
        mst = new UndirectedGraph();
        pq= new PriorityQueue<>(Comparator.comparingDouble(Edge::getWeight));
        visited = new HashMap<>();

        //Get all vertices from the graph object
        List<Vertex> vertices= new ArrayList<>(graph.getAllVertices());

        //Random class to select the source starting point randomly
        this.start= pickArbitraryStart(vertices);

        //Iterate over all the vertices in the graph and store them in visited hashmap as unvisited
        for(Vertex v: vertices){
            //Add the vertex v in visited set and set the value to false
            visited.put(v, false);
            //Add the vertex v in the MST
            mst.addVertex(v);
        }
    }

    /**
     * Method creates a minimum spanning tree from the provided undirected graph
     * @return Returns the MST graph
     * @throws Exception
     */
    public Graph getMinimumSpanningTree() throws Exception {
        //Visit the initial vertex and add all adjacent vertices in the pq
        visit(this.start);

        //Iterate over all the edges in the min heap and process all adjacent vertices in sequence
        while(!pq.isEmpty()){
            Edge e= pq.poll();
            Vertex v1= e.getSource();
            Vertex v2= e.getDestination();
            //continue if both vertices in the edge are already visited
            if(visited.get(v1) && visited.get(v2))
                continue;

            //Add the edge in the MST if vertices are not visited
            mst.addEdge(v1, v2, e.getWeight());

            //Visit the remaining vertex if not visited yet
            if(!visited.get(v1))
                visit(v1);
            if(!visited.get(v2))
                visit(v2);
        }
        return mst;
    }

    /**
     * Method to visit the vertex v and add it's adjacent vertices in the minimum priority queue
     * @param v vertex which should be visited
     * @throws Exception
     */
    private void visit(Vertex v) throws Exception {
        //Mark the vertex as visited
        visited.put(v, true);

        //Get and iterate over all adjacent edges connected to v
        for(Edge e: this.graph.getAllAdjacentEdgesOfVertex(v)){
            //Check if the v is source or destination of the edge, and
            //add unvisited vertex accordingly to the pq
            if(e.getSource() == v){
                if(!visited.get(e.getDestination()))
                    pq.add(e);
            }else{
                if(!visited.get(e.getSource()))
                    pq.add(e);
            }
        }
    }

    /**
     * Method to pick arbitrary vertex as a starting point from a graph
     * @param vertices All vertices of a graph
     * @return returns a random start vertex
     */
    private Vertex pickArbitraryStart(final List<Vertex> vertices){
        Random rand= new Random();
        int i= rand.nextInt(vertices.size());
        return vertices.get(i);
    }

    /**
     * Method to get the total cost of the MST
     * @return Cost of the MST tree
     */
    public double getMSTCost() {
        double cost = 0;
        for (Edge e : mst.getAllEdges()) {
            cost += e.getWeight();
        }
        return cost/2;
    }

}
