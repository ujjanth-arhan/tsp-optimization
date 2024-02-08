package org.info6205.tsp.optimizations;

import org.info6205.tsp.core.Graph;
import org.info6205.tsp.core.Vertex;
import org.info6205.tsp.util.GraphUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private int iterations;
    private double temp;
    private List<Vertex> tour;
    private double gamma;

    public SimulatedAnnealing(List<Vertex> tour, int iterations, double temp, double gamma) {
        this.tour = tour;
        this.iterations = iterations;
        this.temp = temp;
        this.gamma = gamma;
    }

    public List<Vertex> optimize() {
        double currentCost = GraphUtil.getTotalCostOfTour(tour);
        double newCost = currentCost;
        for (int it=0; it < this.iterations; it++) {
            int vertexCount = tour.size()-1;

            Random rand = new Random();
            int i = rand.nextInt(vertexCount-4);
            int j = rand.nextInt(vertexCount-2);
            while(j < i){
                j= rand.nextInt(vertexCount -2);
            }
            int k= rand.nextInt(vertexCount );
            while( k < j ){
                k = rand.nextInt(vertexCount);
            }

            ThreeOptForSA threeOpt= new ThreeOptForSA(tour);
            double delta = threeOpt.doThreeOpt(i, j, k);
            newCost = newCost + delta;
            if(delta < 0){
                currentCost = newCost;
                this.tour = threeOpt.getTour();
            }else{
                if(shouldAccept(newCost, currentCost)){
                    currentCost = newCost;
                    this.tour = threeOpt.getTour();
                }
            }
            if(it%100 ==0 ){
                coolTemp();
            }
            //coolTemp();
        }
        return tour;
    }

    private void coolTemp() {
        this.temp = temp * gamma;
    }

    private boolean shouldAccept(double newCost, double currentCost) {
        double prob = Math.exp((currentCost - newCost) / this.temp);
        Random rand = new Random();
        if (newCost < currentCost) {
            //accept new solution
            return true;
        } else {
            //System.out.println(rand.nextDouble()+"\t"+prob);
            if (rand.nextDouble() < prob) {
                //accept new solution
                return true;
            }
        }
        return false;
    }
}
