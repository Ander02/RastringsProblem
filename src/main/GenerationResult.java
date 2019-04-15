package main;

import java.util.Arrays;

public class GenerationResult {
    
    public int generation;
    public int[] bestChromoson;
    public double avarageFitness; 

    public GenerationResult(int generation, int[] bestChromoson, double avarageFitness) {
        this.generation = generation;
        this.bestChromoson = Arrays.copyOfRange(bestChromoson, 0, bestChromoson.length);
        this.avarageFitness = avarageFitness;
    }
        
    public double getBestFitness()
    {
        return Rastrings.fitness(bestChromoson);
    };   

    @Override
    public String toString() {
        return "Generation: " + this.generation + "\t BestFitness: " + this.getBestFitness() + "\t AvarageFitness: " + this.avarageFitness;
    }
    
    
}
