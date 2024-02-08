package org.info6205.tsp.core;

import org.apache.lucene.util.SloppyMath;

public class Edge implements Comparable<Edge>{

    /**
     * Source vertex of the edge
     */
    private Vertex source;

    /**
     * Destination vertex of the edge
     */
    private Vertex destination;

    /**
     * Weight of the edge
     */
    private double weight;

    /**
     * Parameterized constructor allowing custom weight
     * @param source Source vertex of the edge
     * @param destination Destination vertex of the edge
     * @param weight Weight of the edge
     */
    public Edge(Vertex source, Vertex destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Parameterized constructor which assigns weight to the edge
     * Weight is calculated using haversine formula
     * @param source Source vertex of the edge
     * @param destination Destination vertex of the edge
     */
    public Edge(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
        this.weight = calculateDistanceInMeters();
    }

    /**
     * Getter for the source vertex
     * @return Source vertex of the edge
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * Getter for the destination vertex
     * @return Destination vertex of the edge
     */
    public Vertex getDestination() {
        return destination;
    }

    /**
     * Getter for the edge weight
     * @return Weight of the edge
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Method to calculate Euclidean distance without overflow or underflow
     *
     * @return Euclidean distance between source and destination
     */
    private double calculateEuclideanDistance(){
        return Math.hypot(destination.getXPos()-source.getXPos(),destination.getYPos()-source.getYPos());
    }

    /**
     * Distance calculated using the Haversine formula
     * @return Distance between two vertices
     */
    private double calculateDistanceInMeters(){
        return SloppyMath.haversinMeters(source.getXPos(), source.getYPos(), destination.getXPos(), destination.getYPos());
    }

    /**
     * Overriding default implementation of toString()
     * @return Custom string containing all properties of the edge
     */
    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", destination=" + destination +
                ", weight=" + weight +
                '}';
    }

    /**
     * Compares the current edge with an another edge
     * @param o the edge object to be compared.
     * @return -1 if current edge weight is less than the edge we are comparing, 0 if weight is equal and 1 if weight is more
     */
    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.getWeight(), o.getWeight());
    }
}
