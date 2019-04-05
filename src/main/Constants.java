package main;

import java.util.Random;

public abstract class Constants {

    /**
     * The random Seed
     */
    //Obj. que gera numeros aleatórios
    public static final Random randomSeed = new Random();

    //Rates
    
    public static final double mutationRate = 0.0007;
    public static final double crossoverRate = 0.8;

    /**
     * The decimal value constant
     */
    public static final double decimalJump = 0.00978;

    //Indexes
    
    public static final int numberOfBytes = 10;
    public static final int startX = 0;
    public static final int endX = numberOfBytes - 1;
    public static final int startY = startX + numberOfBytes;
    public static final int endY = endX + numberOfBytes;
    /*
    ----X----- ----Y-----
    ********** **********
    
     */
}
