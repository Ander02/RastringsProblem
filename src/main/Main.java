package main;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        long seed;

        Random random = new Random();

        //AllHyperParametersCombination(random);
        HyperParameters hyperParameters = new HyperParameters();
        seed = random.nextLong();
        System.out.println("SEED: " + seed);
        hyperParameters.randomSeed = new Random(seed);
        Rastrings rastrings = new Rastrings(hyperParameters);

        List<GenerationResult> results = rastrings.start(50);

        results.forEach((GenerationResult result) -> {
            System.out.println(result.toString());
        });
    }

    public static Map<HyperParameters, List<GenerationResult>> AllHyperParametersCombination(long seed) {
        Map<HyperParameters, List<GenerationResult>> experiment = new TreeMap<>();

        long i = 0;
        for (double crossover = 0.50; crossover < 1.0; crossover += 0.10) {
            for (double mutationRate = 0.0005; mutationRate < 0.001; mutationRate += 0.0001) {
                for (double selectTheBestInTournament = 0.8; selectTheBestInTournament < 1; selectTheBestInTournament += 0.1) {
                    for (int populationSize = 0; populationSize < 300; populationSize += 10) {
                        for (int tournamentLength = 0; tournamentLength < populationSize / 2; tournamentLength += 10) {
                            if (tournamentLength != 0 && populationSize != 0) {
                                long t1 = System.currentTimeMillis();
                                HyperParameters hyperParameters = new HyperParameters(crossover, mutationRate, populationSize, tournamentLength, selectTheBestInTournament, seed);
                                Rastrings rastrings = new Rastrings(hyperParameters);
                                experiment.put(hyperParameters, rastrings.start(50));
                                long t2 = System.currentTimeMillis();
                                System.out.println("Index: " + (i++) + "\tTime: " + (t2 - t1) + "ms");
                            }
                        }
                    }
                }
            }
        }

        System.out.println("SEED " + seed);
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

        return experiment;
    }

}
