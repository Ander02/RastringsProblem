package main;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        Map<HyperParameters, List<GenerationResult>> experiment = new TreeMap<>();
        Random random = new Random(42);

        long i = 0;
        for (double crossover = 0.50; crossover < 1.0; crossover += 0.10) {
            for (double mutationRate = 0.0005; mutationRate < 0.001; mutationRate += 0.0001) {
                for (int populationSize = 0; populationSize < 300; populationSize += 50) {
                    for (int tournamentLength = 0; tournamentLength < populationSize / 2; tournamentLength += 10) {
                        if (tournamentLength != 0 && populationSize != 0) {
                            HyperParameters hyperParameters = new HyperParameters(crossover, mutationRate, populationSize, tournamentLength, random);
                            Rastrings rastrings = new Rastrings(hyperParameters);
                            experiment.put(hyperParameters, rastrings.start(50));
                            System.out.println("Index: " + i++);
                        }
                    }
                }
            }
        }
        
        System.out.println("===========================================");
        experiment.forEach((HyperParameters parameters, List<GenerationResult> results) -> {

            GenerationResult bestResult = results.get(0);
            if (bestResult.getBestFitness() <= 1.0E-3) {
                System.out.println("HyperParameters:");
                System.out.println(parameters.toString());
                System.out.println();
                System.out.println("BEST RESULT: " + results.get(0).toString());
                System.out.println("===========================================");
            }
        });
    }
}
