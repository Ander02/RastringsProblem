package main;

import java.awt.dnd.InvalidDnDOperationException;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Anderson
 */
public class Rastrings {

    public int numberOfGenerations = 0;

    public List<int[]> population;

    public Rastrings() {
        population = new LinkedList<>();
    }

    public Rastrings(int populationNumber) {
        if (populationNumber < 0) {
            throw new InvalidDnDOperationException("The number of the population shold be greater than zero");
        }

        for (int i = 0; i < populationNumber; i++) {
            population.add(generateRandomChromosom(Constants.numberOfBytes));
        }
    }

    public void start(int numberOfGenerations) {
        //Select in the population

        //Crossover
        //Mutate
        //UpdatePopulation
        //Increment numberOfGenerations
    }

    public static int[] generateRandomChromosom(int numberOfBytes) {
        Random random = new Random();

        int[] newChromosomes = new int[2 * numberOfBytes];

        for (int i = 0; i < newChromosomes.length; i++) {
            boolean bool = random.nextBoolean();
            newChromosomes[i] = bool ? 1 : 0;
        }

        return newChromosomes;
    }
}
