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
            for (int i = 0; i < population.size(); i++) {

                //Select in the population based on fitness
                List<int[]> selectedMembers = this.select();

                if (this.canCross()) {
                    crossover(selectedMembers);
                } else {
                    clone(selectedMembers);
                }

                //Mutate
                for (int[] selectedMember : selectedMembers) {
                    for (int j = 0; j < selectedMember.length; j++) {
                        if (this.canMutade()) {
                            selectedMember[j] = mutate(selectedMember[j]);
                        }
                    }
                }

                //UpdatePopulation
                //Increment numberOfGenerations
                currentGeneration++;
            }
        }
    }

    private static int[] generateRandomChromosom(int numberOfBytes) {

        int[] newChromosomes = new int[2 * numberOfBytes];

        for (int i = 0; i < newChromosomes.length; i++) {
            boolean randomBool = Constants.randomSeed.nextBoolean();
            newChromosomes[i] = randomBool ? 1 : 0;
        }

        return newChromosomes;
    }

    public List<int[]> select() {
        return null;
    }

    public void crossover(List<int[]> selectedChromosomes) {
    }

    public void clone(List<int[]> selectedChromosomes) {
    }

    public static double fitness(int[] chromosom) {
        //TODO: Calculate x and y
        double x = 0;
        double y = 0;

        return Math.abs(20 + Math.pow(x, 2) + Math.pow(y, 2) - 10 * (Math.cos(Math.PI * x) + Math.cos(Math.PI * y)));
    }

    public boolean canMutade() {
        return canOccour(Constants.mutationRate);
    }

    public boolean canCross() {
        return canOccour(Constants.crossoverRate);
    }

    public int mutate(int genToMutate) {
        return genToMutate == 1 ? 0 : 1;
    }

    private static boolean canOccour(double probability) {
        return Constants.randomSeed.nextDouble() <= probability;
    }

}
