package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Anderson
 */
public class Rastrings {

    public List<int[]> population;

    public Rastrings() {
        this.population = new LinkedList<>();
    }

    public Rastrings(int populationNumber) {
        this();
        if (populationNumber < 0) {
            throw new RuntimeException("Population number should be greather or equal than 0");
        }

        for (int i = 0; i < populationNumber; i++) {
            this.population.add(generateRandomChromosom(Constants.numberOfBytes));
        }
        Utility.printList(this.population, "Initial population");
    }

    public void start(int numberOfGenerations) {
        int currentGeneration = 0;

        while (currentGeneration < numberOfGenerations) {
            List<int[]> newGeneration = new ArrayList<>();
            for (int i = 0; i < population.size(); i++) {

                //Select in the population based on fitness
                int[] selectedMemberOne = this.tournamentSelection(Constants.tournamentLength);
                int[] selectedMemberTwo = this.tournamentSelection(Constants.tournamentLength);

                int[] son;
                if (this.canCross()) {
                    son = crossover(selectedMemberOne, selectedMemberTwo);
                } else {
                    son = clone(selectedMemberOne, selectedMemberTwo);
                }

                //Mutate - Foreach chromoson, verify if can mutate, if true, change the value
                for (int j = 0; j < son.length; j++) {
                    if (this.canMutade()) {
                        selectedMemberOne[j] = mutate(selectedMemberOne[j]);
                    }
                }

                newGeneration.add(selectedMemberOne);
            }
            this.population = new ArrayList<>(newGeneration);
            Utility.printList(this.population, "Population " + currentGeneration);
            currentGeneration++;
            this.population.sort(new FitnessComparator());
        }
        System.out.println("===========================================");
        Utility.printList(population, "Final Population");
    }

    private static int[] generateRandomChromosom(int numberOfBytes) {

        int[] newChromosomes = new int[2 * numberOfBytes];

        for (int i = 0; i < newChromosomes.length; i++) {
            boolean randomBool = Constants.randomSeed.nextBoolean();
            newChromosomes[i] = randomBool ? 1 : 0;
        }

        return newChromosomes;
    }

    public int[] rankSelection() {
        this.population.sort(new FitnessComparator());
        //TODO:
        return this.population.get(0);
    }

    public int[] tournamentSelection(int tournamentLength) {
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < tournamentLength; i++) {
            list.add(this.population.get(Constants.randomSeed.nextInt(this.population.size())));
        }
        list.sort(new FitnessComparator());

        int[] selected = list.get(0);
        return Arrays.copyOfRange(selected, 0, selected.length);
    }

    public int[] crossover(int[] selectedChromosom, int[] otherSelectedChromosom) {
        int pointTwo = Constants.randomSeed.nextInt(selectedChromosom.length);
        int pointOne = 0;
        if (pointTwo != 0) {
            pointOne = Constants.randomSeed.nextInt(pointTwo);
        }

        int[] son = new int[selectedChromosom.length];

        for (int i = 0; i < pointOne; i++) {
            son[i] = selectedChromosom[i];
        }
        for (int i = pointOne; i < pointTwo; i++) {
            son[i] = otherSelectedChromosom[i];
        }
        for (int i = pointTwo; i < selectedChromosom.length; i++) {
            son[i] = selectedChromosom[i];
        }

        return son;
    }

    public static double fitness(int[] chromosom) {
        double x = Utility.toInt(Arrays.copyOfRange(chromosom, Constants.startX, Constants.endX + 1)) * Constants.decimalJump - 5;
        double y = Utility.toInt(Arrays.copyOfRange(chromosom, Constants.startY, Constants.endY + 1)) * Constants.decimalJump - 5;

        return Math.abs(20 + Math.pow(x, 2) + Math.pow(y, 2) - 10 * (Math.cos(Math.PI * x) + Math.cos(Math.PI * y)));
    }

    public int mutate(int genToMutate) {
        return genToMutate == 1 ? 0 : 1;
    }

    public boolean canMutade() {        
        return canOccour(Constants.mutationRate);
    }

    public boolean canCross() {
        return canOccour(Constants.crossoverRate);
    }

    private static boolean canOccour(double probability) {
        return Constants.randomSeed.nextDouble() <= probability;
    }

    private int[] clone(int[] selectedMemberOne, int[] selectedMemberTwo) {
        double firtsFitness = fitness(selectedMemberOne);
        double secondFitness = fitness(selectedMemberTwo);

        if (firtsFitness >= secondFitness) {
            return Arrays.copyOfRange(selectedMemberOne, 0, selectedMemberOne.length);
        } else {
            return Arrays.copyOfRange(selectedMemberTwo, 0, selectedMemberTwo.length);
        }
    }

    private static class FitnessComparator implements Comparator<int[]> {

        @Override
        public int compare(int[] chromosom, int[] otherChromosom) {
            double fitnessOne = fitness(chromosom);
            double fitnessTwo = fitness(otherChromosom);
            double diff = fitnessOne - fitnessTwo;
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return - 1;
            } else {
                return 0;
            }
        }
    }
}
