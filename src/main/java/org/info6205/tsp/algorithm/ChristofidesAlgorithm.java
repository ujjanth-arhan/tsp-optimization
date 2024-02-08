package org.info6205.tsp.algorithm;

import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.UndirectedSubGraph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.util.GraphUtil;
import org.info6205.tsp.visualization.TSPVisualization;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ChristofidesAlgorithm {

    /**
     * Initial input graph
     */
    Graph graph;

    /**
     * Parameterized constructor taking inital graph as input
     * @param graph
     */
    public ChristofidesAlgorithm(Graph graph) {
        this.graph = graph;
    }

    /**
     * Generates a new TSP tour (Not necessarily the same tour each time)
     * @return A TSP tour containing a list of vertices
     */
    public List<Vertex> generateTSPTour() throws Exception{

        // Creating minimum spanning tree algorithm class instance
        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(graph);

        //Getting minimum spanning tree
        Graph mst = minimumSpanningTree.getMinimumSpanningTree();

        //Creating subgraph for greedy perfect matching input
        Graph subGraph = new UndirectedSubGraph(mst.getOddDegreeVertices(), graph);

        //Creating Greedy perfect matching class instance
        GreedyPerfectMatching greedyPerfectMatching = new GreedyPerfectMatching(subGraph);

        //Adding existing edges to minimum spanning tree to create multigraph
        mst.addExistingEdgesToGraph(greedyPerfectMatching.getPerfectMatching().getAllEdges().stream().collect(Collectors.toList()));

        //Creating Hierholzer Eulerian Circuit class instance
        HierholzerEulerianCircuit hierholzerEulerianCircuit = new HierholzerEulerianCircuit(mst);

        //Getting a Eulerian Circuit
        List<Vertex> eulerianCircuit = hierholzerEulerianCircuit.getEulerianCircuit();

        return GraphUtil.getTSPTour(eulerianCircuit);
    }
}
