package main;

import java.util.Random;

public class HyperParameters implements Comparable<HyperParameters> {

    public Random randomSeed = new Random(1);

    //Rates
    public double crossoverRate = 0.8;
    public double mutationRate = 0.0009;

    public int tournamentLength = 10;
    public int populationSize = 100;

    public HyperParameters() {
    }

    public HyperParameters(double crossoverRate, double mutationRate, int populationSize, int tournamentLength, Random randomSeed) {
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.tournamentLength = tournamentLength;
        this.populationSize = populationSize;
        this.randomSeed = randomSeed;
    }

    @Override
    public String toString() {
        return "CrossoverRate: " + crossoverRate
                + "\nMutationRate: " + mutationRate
                + "\nPopulationSize: " + populationSize
                + "\nTournamentLength: " + tournamentLength;
    }

    @Override
    public int compareTo(HyperParameters otherHyperParameter) {
        double crossover = this.crossoverRate - otherHyperParameter.crossoverRate;
        double mutation = this.mutationRate - otherHyperParameter.mutationRate;
        int tournament = this.tournamentLength - otherHyperParameter.tournamentLength;
        int population = this.populationSize - otherHyperParameter.populationSize;

        double fullResult = population - tournament - crossover - mutation;

        if (fullResult > 0) {
            return 1;
        } else if (fullResult < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
