package org.info6205.tsp.core;

public class Vertex{

    /**
     * Unique identifier of the vertex
     */
    private long id;

    /**
     * Latitude of the vertex
     */
    private double xPos;

    /**
     * Longitude of the vertex
     */
    private double yPos;

    /**
     * Parameterized constructor for vertex
     * @param id Unique identifier of the vertex
     * @param xPos Latitude of the vertex
     * @param yPos Longitude of the vertex
     */
    public Vertex(long id, double xPos, double yPos) {
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Getter for id
     * @return Unique identifier of the vertex
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for latitude of vertex
     * @return Latitude of the vertex
     */
    public double getXPos() {
        return xPos;
    }

    /**
     * Getter for longitude of vertex
     * @return Longitude of the vertex
     */
    public double getYPos() {
        return yPos;
    }

    /**
     * Checks if two vertices have the same id
     * @param o Second vertex to be checked for equality
     * @return true if vertex has same unique identifier else false
     */
    @Override
    public boolean equals(Object o){
        Vertex v = (Vertex) o;
        return this.getId() == v.getId();
    }

    /**
     * Overriding default toString() method
     * @return Custom string containing id of the vertex
     */
    @Override
    public String toString() {
        return id+"";
    }
}
