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
    private HyperParameters hyperParameters;

    public Rastrings() {
        this.population = new LinkedList<>();
    }

    public Rastrings(HyperParameters hyperParameters) {
        this();
        this.hyperParameters = hyperParameters;

        for (int i = 0; i < hyperParameters.populationSize; i++) {
            this.population.add(PopulationGenerator.generateRandomChromosom(Constants.numberOfBytes));
        }
        //Utility.printList(this.population, "Initial population");
    }

    public List<GenerationResult> start(int numberOfGenerations) {
        int currentGeneration = 0;

        List<GenerationResult> result = new LinkedList<>();
        result.add(this.getGenerationResult(currentGeneration));
        while (currentGeneration < numberOfGenerations) {
            List<int[]> newGeneration = new ArrayList<>();
            for (int i = 0; i < population.size(); i++) {

                //Select in the population based on fitness
                int[] selectedMemberOne = this.tournamentSelection(hyperParameters.tournamentLength);
                int[] selectedMemberTwo = this.tournamentSelection(hyperParameters.tournamentLength);

                //Cross or clone selected members
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
            //Utility.printList(this.population, "Population " + currentGeneration);
            currentGeneration++;
            result.add(this.getGenerationResult(currentGeneration));
            //this.population.sort(new FitnessComparator());
        }
        //Utility.printList(population, "Final Population");

        result.sort(new BestFitnessGenerationComparator());
        return result;
    }

    private GenerationResult getGenerationResult(int generation) {
        List<int[]> auxList = new LinkedList<>();
        this.population.forEach((chromoson) -> {
            auxList.add(Arrays.copyOf(chromoson, chromoson.length));
        });

        double sum = 0;
        for (int[] chromoson : auxList) {
            sum += fitness(chromoson);
        }

        auxList.sort(new FitnessComparator());
        return new GenerationResult(generation, auxList.get(0), sum / auxList.size());
    }

    public int[] tournamentSelection(int tournamentLength) {
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < tournamentLength; i++) {
            list.add(this.population.get(hyperParameters.randomSeed.nextInt(this.population.size())));
        }
        list.sort(new FitnessComparator());

        int[] selected;
        if (getTheBest()) {
            selected = list.get(0);
        } else {
            selected = list.get(list.size() - 1);
        }
        return Arrays.copyOfRange(selected, 0, selected.length);
    }

    public int[] crossover(int[] selectedChromosom, int[] otherSelectedChromosom) {
        int pointTwo = hyperParameters.randomSeed.nextInt(selectedChromosom.length);
        int pointOne = 0;
        if (pointTwo != 0) {
            pointOne = hyperParameters.randomSeed.nextInt(pointTwo);
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

    public boolean getTheBest() {
        return canOccour(hyperParameters.getTheBest);
    }

    public boolean canMutade() {
        return canOccour(hyperParameters.mutationRate);
    }

    public boolean canCross() {
        return canOccour(hyperParameters.crossoverRate);
    }

    private boolean canOccour(double probability) {
        return hyperParameters.randomSeed.nextDouble() <= probability;
    }

    private int[] clone(int[] selectedMemberOne, int[] selectedMemberTwo) {
        double firtsFitness = fitness(selectedMemberOne);
        double secondFitness = fitness(selectedMemberTwo);

        if (firtsFitness >= secondFitness) {
            if (getTheBest()) {
                return Arrays.copyOfRange(selectedMemberOne, 0, selectedMemberOne.length);
            } else {
                return Arrays.copyOfRange(selectedMemberTwo, 0, selectedMemberOne.length);
            }
        } else {
            if (getTheBest()) {
                return Arrays.copyOfRange(selectedMemberTwo, 0, selectedMemberTwo.length);
            } else {
                return Arrays.copyOfRange(selectedMemberOne, 0, selectedMemberOne.length);
            }
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

    private static class AverageFitnessGenerationComparator implements Comparator<GenerationResult> {

        @Override
        public int compare(GenerationResult first, GenerationResult second) {
            double fitnessOne = first.averageFitness;
            double fitnessTwo = second.averageFitness;
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

    private static class BestFitnessGenerationComparator implements Comparator<GenerationResult> {

        @Override
        public int compare(GenerationResult first, GenerationResult second) {
            double fitnessOne = first.getBestFitness();
            double fitnessTwo = second.getBestFitness();
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
