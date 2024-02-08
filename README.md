# Travelling Salesman Problem (TSP)

This repository contains an implementation of the Travelling Salesman Problem (TSP) using the Christofides Algorithm and various optimization techniques.

## Problem Statement

Given a set of cities and their pairwise distances, the objective of TSP is to find the shortest possible route that visits all cities exactly once and returns to the starting point.

## Implemented Algorithms

We have implemented the following algorithms for solving TSP:

1. **Christofides Algorithm**: This is a heuristic algorithm that provides a guaranteed approximation of the optimal solution.
2. **2-opt Optimization**: This optimization technique iteratively removes two edges from the tour and reconnects them in a different order to reduce the total distance.
3. **3-opt Optimization**: This optimization technique iteratively removes three edges from the tour and reconnects them in a different order to reduce the total distance.
4. **Simulated Annealing Optimization**: This optimization technique is a probabilistic metaheuristic that iteratively accepts worse solutions with a certain probability in the hope of escaping local optima and finding the global optimum.
5. **Ant Colony Optimization**: This optimization technique is based on the behavior of ants in finding the shortest path between their nest and a food source. It uses pheromone trails to guide the search and iteratively updates them based on the quality of the solutions found.

## Usage

### Console output
- Run the **TSPMain.java** class from the *driver* package to run Christofides Algorithm 
- Run any **TSPMainWith\*.java** class from the *driver* package to run Christofides Algorithm along with your desired optimization

For every driver class there is a corresponding csv file generated in the root directory giving the best tour that has been found. It is in the initial format of the input with the crimeID modified to only include the last 5 digits.

**Note: Our best tour is acheived using Simulated Annealing. So please run the TSPMainWithSA class for the best tour result. It takes approximately 15min to run. Decrease the number of iterations if required**

### Visualization
- Run **TSPMainVisualization.java** class from the *driver* package to see a simulation of the whole algorithm

