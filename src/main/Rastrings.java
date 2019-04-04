package main;

import java.awt.dnd.InvalidDnDOperationException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Anderson
 */
public class Rastrings {

    public int numberOfGenerations = 0;

    public List<int[]> population;

    public Rastrings() {
        this.population = new LinkedList<>();
    }

    public Rastrings(int populationNumber) {
        if (populationNumber < 0) {
            throw new InvalidDnDOperationException("The number of the population shold be greater than zero");
        }

        for (int i = 0; i < populationNumber; i++) {
            this.population.add(generateRandomChromosom(Constants.numberOfBytes));
        }
    }

    public void start(int numberOfGenerations) {
        int currentGeneration = 0;

        while (currentGeneration < this.numberOfGenerations) {
            //Select in the population based on fitness

            //Crossover or clonning
            //Mutate
            //UpdatePopulation
            //Increment numberOfGenerations
            currentGeneration++;
        }
    }

    public static int[] generateRandomChromosom(int numberOfBytes) {

        int[] newChromosomes = new int[2 * numberOfBytes];

        for (int i = 0; i < newChromosomes.length; i++) {
            boolean randomBool = Constants.randomSeed.nextBoolean();
            newChromosomes[i] = randomBool ? 1 : 0;
        }

        return newChromosomes;
    }
}
