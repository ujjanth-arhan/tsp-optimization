package org.info6205.tsp.optimizations;

import org.info6205.tsp.core.Edge;
import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.util.GraphUtil;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Ant colony implementation to attempt TSP solution
 */
public class AntColonyOptimization {

    /**
     * The probability matrix based on which the node is chosen
     */
    double[][] probabilityMatrix;

    /**
     * The distance between one node to another node
     */
    double[][] distanceMatrix;

    /**
     * Holds the weight of the pheromone trail of the ants
     */
    double[][] rewardMartrix;

    /**
     * Graph containing vertices, edges and their weights
     */
    Graph graph;

    /**
     * Holds the list of edges from one node to another
     */
    List<Edge> edges;

    /**
     * Total number of vertices
     */
    int length;

    /**
     * To adjust the impact of distance in probability matrix
     */
    public double alpha;

    /**
     * To adjust the impact of reward in probability matrix
     */
    public double beta;

    /**
     * To adjust the decay rate of the reward matrix
     */
    public double decay;

    /**
     * The list of vertices
     */
    List<Vertex> vertices;

    /**
     * Meant to initialize the parameter that are going to be used throughout the class
     * @param graph graph containing vertices, edges and weights
     */
    public AntColonyOptimization(Graph graph) {
        vertices = new ArrayList<>(graph.getAllVertices().stream().sorted(Comparator.comparingLong(Vertex::getId)).collect(Collectors.toList()));
        length = vertices.size();
        distanceMatrix = new double[length][length];
        probabilityMatrix = new double[length][length];
        rewardMartrix = new double[length][length];
        this.edges = new ArrayList<>(graph.getAllEdges().stream().sorted().collect(Collectors.toList()));
        this.graph = graph;
        this.alpha = 24.0;
        this.beta = 25.0;
    }

    /**
     * Entry method to this class which starts the ant colony implementation
     * @return optimized tour
     */
    public List<Vertex> startOptimization() {
        initializeDistanceMatrix();
        initializeRewardMatrix();
        initializeProbabilityMatrix();

        double minTour = Double.MAX_VALUE;
        double minBatchTour = Double.MAX_VALUE;
        Random random = new Random();
        List<Vertex> minCircuit = new ArrayList<>();
        List<Vertex> minBatchCircuit = new ArrayList<>();
        double prevPheromoneTrail = 0.0;
        for (int i = 1; i <= 200; i++) {
            List<Integer> tour = calculateAntColonyTour(random.nextInt(length - 1));
            List<Vertex> tourVertices = new ArrayList<>();
            for (int v : tour) {
                tourVertices.add(vertices.get(v));
            }

            double tourCost = GraphUtil.getTotalCostOfTour(tourVertices);
            minTour = minTour > tourCost ? tourCost : minTour;
            minCircuit = tourVertices;
            if (minBatchTour > tourCost) {
                minBatchTour = tourCost;
                minBatchCircuit = tourVertices;
            }
            if (i % 10 == 0) {
                TwoOptSwapOptimization twoOptSwapOptimization = new TwoOptSwapOptimization(minBatchCircuit);
                minBatchCircuit = twoOptSwapOptimization.getOptimumTour();

                if (GraphUtil.getTotalCostOfTour(minBatchCircuit) < minTour) {
                    minTour = minBatchTour;
                    minCircuit = minBatchCircuit;
                }

                updateRewards(minBatchTour, prevPheromoneTrail, minBatchCircuit);
                minBatchTour = Double.MAX_VALUE;
            }
            initializeProbabilityMatrix();
            prevPheromoneTrail = tourCost;
        }

        return minCircuit;
    }

    /**
     * Update pheromone matrix based on the tour taken by the ant
     * @param tourCost the cost of the tour taken by the ant
     * @param prevPheromoneTrail keeps track of the tour taken by the previous ant
     * @param circuit the path taken by the ant
     */
    public void updateRewards(double tourCost, double prevPheromoneTrail, List<Vertex> circuit) {
        for (int i = 0; i < circuit.size() - 1; i++) {
            int first = (int) circuit.get(i).getId();
            int second = (int) circuit.get(i+1).getId();
            rewardMartrix[first][second] += rewardMartrix[first][second]/tourCost;
            rewardMartrix[second][first] += rewardMartrix[first][second]/tourCost;
            rewardMartrix[first][second] = (1 - decay) * rewardMartrix[first][second] + decay * (tourCost - prevPheromoneTrail);
            rewardMartrix[second][first] = (1 - decay) * rewardMartrix[second][first] + decay * (tourCost - prevPheromoneTrail);
        }

        for (int i = 0; i < rewardMartrix.length; i++) {
            for (int j = 0; j < rewardMartrix[i].length; j++) {
                double decayFactor = (1 - decay) * rewardMartrix[i][j] + decay * (tourCost - prevPheromoneTrail);
                rewardMartrix[i][j] = decayFactor;
            }
        }
    }

    /**
     * To recalculate the probability matrix after the edge has been visited by an ant
     * @param unvisited list of unvisited nodes
     * @param source the entry point of the circuit
     */
    private void recalculateProbabilityMatrix(Set<Integer> unvisited, int source) {
        double totalInverseRewardDistance = 0.0;
        for (int i: unvisited) {
            double inverseDistance = Math.pow(1/distanceMatrix[source][i], alpha) * Math.pow(rewardMartrix[source][i], beta);
            totalInverseRewardDistance += inverseDistance;
        }

        for (int i: unvisited) {
            probabilityMatrix[source][i] = Math.pow(1/distanceMatrix[source][i], alpha) * Math.pow(rewardMartrix[source][i], beta)/totalInverseRewardDistance;
        }
    }

    /**
     * Run ant colony
     * @param source the start point of the tour
     * @return the path taken by the ant from source to every other node and then back to source
     */
    private List<Integer> calculateAntColonyTour(int source) {
        List<Integer> res = new ArrayList<>();
        Set<Integer> unvisited = new HashSet<>();
        for (int i = 0; i < length; i++) if(i != source) unvisited.add(i);
        int iterNode = source;
        res.add(iterNode);
        Random random = new Random();
        while(unvisited.size() != 0) {
            double numberPicked = random.nextDouble();
            double probabilityRuler = 0.0;
            for (int i: unvisited) {
                probabilityRuler += probabilityMatrix[iterNode][i];
                if (numberPicked <= probabilityRuler) {
                    iterNode = i;
                    break;
                }
            }

            unvisited.remove(Integer.valueOf(iterNode));
            res.add(iterNode);
            recalculateProbabilityMatrix(unvisited, iterNode);
        }

        res.add(source);
        return res;
    }

    /**
     * Initialize the probability matrix based on distance, reward matrix and decay
     */
    private void initializeProbabilityMatrix() {
        for (int i = 0; i < distanceMatrix.length; i++) {
            double totalInverseRewardDistance = 0.0;
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                totalInverseRewardDistance += Math.pow(1/distanceMatrix[i][j], alpha) * Math.pow(rewardMartrix[i][j], beta);
            }

            for (int j = 0; j < distanceMatrix[i].length; j++) {
                probabilityMatrix[i][j] = Math.pow(1/distanceMatrix[i][j], alpha) * Math.pow(rewardMartrix[i][j], beta) / totalInverseRewardDistance;
            }
        }
    }

    /**
     * Initialize reward matrix to 1.0 ensuring that edge has equal initial probability
     */
    private void initializeRewardMatrix() {
        for (int i = 0; i < rewardMartrix.length; i++) {
            for (int j = 0; j < rewardMartrix[i].length; j++) {
                rewardMartrix[i][j] = 1.0;
            }
        }
    }

    /**
     * Initialize distance matrix based on the weight of the edges
     */
    private void initializeDistanceMatrix() {
        for (Edge edge: edges) {
            Vertex source = edge.getSource();
            Vertex destination = edge.getDestination();
            double weight = edge.getWeight();
            if (weight == 0) weight = 1;
            distanceMatrix[(int) source.getId()][(int) destination.getId()] = weight;
            distanceMatrix[(int) destination.getId()][(int) source.getId()] = weight;
        }

        for (int i = 0; i < distanceMatrix.length; i++)
                distanceMatrix[i][i] = 1;
    }
}
