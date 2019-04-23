package main;

import java.util.Random;

public class HyperParameters implements Comparable<HyperParameters> {

    public Random randomSeed;

    //Rates
    public double crossoverRate = 0.8;
    public double mutationRate = 0.007;
    public double getTheBest = 0.9;

    public int tournamentLength = 10;
    public int populationSize = 500;

    public HyperParameters() {
        this.randomSeed = new Random(1);
    }

    public HyperParameters(double crossoverRate, double mutationRate, int populationSize, int tournamentLength, double getBestInTournamentRate, long seed) {
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.tournamentLength = tournamentLength;
        this.populationSize = populationSize;
        this.randomSeed = new Random(seed);
        this.getTheBest = getBestInTournamentRate;
    }

    @Override
    public String toString() {
        return "CrossoverRate: " + crossoverRate
                + "\nMutationRate: " + mutationRate
                + "\nPopulationSize: " + populationSize
                + "\nTournamentLength: " + tournamentLength
                + "\nGetBestInTournamentRate: " + getTheBest;
    }

    @Override
    public int compareTo(HyperParameters otherHyperParameter) {
        String thisIdentifier = new StringBuilder()
                .append(this.crossoverRate)
                .append(this.mutationRate)
                .append(this.tournamentLength)
                .append(this.populationSize)
                .toString();

        String otherIdentifier = new StringBuilder()
                .append(otherHyperParameter.crossoverRate)
                .append(otherHyperParameter.mutationRate)
                .append(otherHyperParameter.tournamentLength)
                .append(otherHyperParameter.populationSize)
                .toString();

        return thisIdentifier.compareTo(otherIdentifier);
    }
}
