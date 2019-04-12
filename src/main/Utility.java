package main;

import java.util.Arrays;
import java.util.List;

/*Biblioteca para métodos auxiliares*/
public class Utility {

    public static int toInt(int[] binArray) {
        int toInt = 0;
        int length = binArray.length - 1;

        for (int i = 0; i <= length; i++) {
            if (binArray[i] == 1) {
                toInt += (int) Math.pow(2, (length - i));
            }
        }
        return toInt;
    }

    public static void printList(List<int[]> list, String label) {
        System.out.println(label);
        int i = 0;
        for (int[] array : list) {
            int[] xArray = Arrays.copyOfRange(array, Constants.startX, Constants.endX + 1);
            int[] yArray = Arrays.copyOfRange(array, Constants.startY, Constants.endY + 1);
            System.out.println();
            System.out.println("index: " + i
                    + "\n"
                    + "\tX: " + Arrays.toString(xArray)
                    + "\tint: " + (Utility.toInt(xArray))
                    + "\tdouble: " + (Utility.toInt(xArray) * Constants.decimalJump - 5)
                    + "\n"
                    + "\tY: " + Arrays.toString(yArray)
                    + "\tint: " + (Utility.toInt(yArray))
                    + "\tdouble: " + (Utility.toInt(yArray) * Constants.decimalJump - 5)
                    + "\n"
                    + "Fitness " + Rastrings.fitness(array));
            i++;
        }
        System.out.println();
    }
}
