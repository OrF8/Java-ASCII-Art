package utils;

/**
 * Providing math utility methods to the program.
 */
public class MathUtils {

    // powers of 2
    private static final int TWO_TO_THE_POWER_OF_0 = 1;
    private static final int TWO_TO_THE_POWER_OF_1 = 2;
    private static final int TWO_TO_THE_POWER_OF_2 = 4;
    private static final int TWO_TO_THE_POWER_OF_3 = 8;
    private static final int TWO_TO_THE_POWER_OF_4 = 16;

    /**
     * Private constructor to prevent instantiation.
     */
    private MathUtils() {}

    /**
     * Returns the closest power of 2 to the given number.
     * @param n The number to find the closest power of 2 to.
     * @return The closest power of 2 to the given number.
     */
    public static int closestPowerOfTwo(int n) {
        if ((n & (n - 1)) == 0) { // If n is a power of two
            return n;
        }

        // Find the next power of 2
        n--; // Decrement n to handle exact powers of 2
        n |= n >> TWO_TO_THE_POWER_OF_0;
        n |= n >> TWO_TO_THE_POWER_OF_1;
        n |= n >> TWO_TO_THE_POWER_OF_2;
        n |= n >> TWO_TO_THE_POWER_OF_3;
        n |= n >> TWO_TO_THE_POWER_OF_4;
        n++;

        return n;
    }
}
