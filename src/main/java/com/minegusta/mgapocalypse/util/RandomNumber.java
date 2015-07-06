package com.minegusta.mgapocalypse.util;

import java.util.Random;

public class RandomNumber {
    private static Random random = new Random();

    /**
     * Return a random number between min and max, min being included and max included as well.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A random number between min and max.
     */
    public static int get(int min, int max) {
        return random.nextInt((max + 1) - min) + min;
    }

    /**
     * Includes max and excludes 0.
     *
     * @param max The maximum number.
     * @return A random Integer between 0 and max. Max being included.
     */
    public static int get(int max) {
        return random.nextInt(max) + 1;
    }

    /**
     * Return a random double.
     *
     * @param max The maximum value.
     * @return A random double.
     */
    public static double getDouble(int max) {
        return ((double) random.nextInt(max) + 1);
    }

    /**
     * It could both be true or false untill it is returned.
     *
     * @return true or false.
     */
    public static boolean getBoolean() {
        return random.nextBoolean();
    }

}
