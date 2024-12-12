package image_char_matching;

import java.util.HashMap;

/**
 * Responsible for matching brightness value to a single character
 * and normalizing it with relation to a character set.
 */
class CharBrightnessMatcher {

    private static final int NUM_OF_PIXELS_IN_CONVERTED_CHARACTER = 16 * 16;

    /**
     * Count the number of true cells in a given boolean matrix.
     *
     * @param boolArray The 2D array to count cells in.
     * @return The number of true cells.
     */
    private static int countTrue(boolean[][] boolArray) {
        int count = 0;
        for (boolean[] booleans : boolArray) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Match a given character with its brightness.
     * @param c The character to match brightness to.
     * @return The brightness of the character.
     */
     /* TODO: Think of access modifier */ static double matchBrightness(char c) {
        boolean[][] convertedChar = CharConverter.convertToBoolArray(c);
        int numTrue = countTrue(convertedChar);
        /*
         The brightness value for each character is the number of true cells in its matrix representation
         divided by the total number of pixels in its matrix representation.
         */
        return numTrue / (double) NUM_OF_PIXELS_IN_CONVERTED_CHARACTER;
    }

    /**
     * Normalize the brightness values in a given character set.
     * Called only when all the characters in the set have an assigned brightness value.
     *
     * @param charSet The character set.
     */
    /* TODO: Think of access modifier */ static void normalizeBrightness(HashMap<Character, Double> charSet) {
        // Calculate the min and max brightness values in the given set.
        double maxBrightness = Double.MIN_VALUE;
        double minBrightness = Double.MAX_VALUE;
        for (char c : charSet.keySet()) {
            double currentBrightness = charSet.get(c);
            if (currentBrightness > maxBrightness) {
                maxBrightness = currentBrightness;
            }
            if (currentBrightness < minBrightness) {
                minBrightness = currentBrightness;
            }
        }

        for (char c : charSet.keySet()) { // Normalize each character according to the min and max brightness.
            double newBrightness = (charSet.get(c) - minBrightness) / (maxBrightness - minBrightness);
            // Because c is already a key in charSet, this will update its value
            charSet.put(c, newBrightness);
        }
    }

}
