package main;

import java.util.Random;

/**
 *
 * @author Anderson
 */
public class PopulationGenerator {

    private static Random populationRandomSeed = new Random(50);
    
    public static int[] generateRandomChromosom(int numberOfBytes) {

        int[] newChromosomes = new int[2 * numberOfBytes];

        for (int i = 0; i < newChromosomes.length; i++) {
            boolean randomBool = populationRandomSeed.nextBoolean();
            newChromosomes[i] = randomBool ? 1 : 0;
        }

        return newChromosomes;
    }
    
    public void setRandomSeed(Random newRandom) {
        PopulationGenerator.populationRandomSeed = newRandom;
    }
}
