package main;

import java.util.Arrays;

public class GenerationResult {
    
    public int generation;
    public int[] bestChromoson;
    public double averageFitness; 

    public GenerationResult(int generation, int[] bestChromoson, double avarageFitness) {
        this.generation = generation;
        this.bestChromoson = Arrays.copyOfRange(bestChromoson, 0, bestChromoson.length);
        this.averageFitness = avarageFitness;
    }
        
    public double getBestFitness()
    {
        return Rastrings.fitness(bestChromoson);
    };   

    @Override
    public String toString() {
        return "Generation: " + this.generation + "\t BestFitness: " + this.getBestFitness() + "\t AvarageFitness: " + this.averageFitness;
    }
    
    
}
