package image_char_matching;

import ascii_art.RoundMethod;

import java.util.HashMap;

/**
 * Responsible for matching an ASCII character to a sub-image with a given brightness.
 * <p>Will be used for the ASCII-Art algorithm to replace sub-images with characters.</p>
 */
public class SubImgCharMatcher {

    private static final int NUM_OF_PIXELS_IN_CONVERTED_CHARACTER = CharConverter.DEFAULT_PIXEL_RESOLUTION *
                                                                    CharConverter.DEFAULT_PIXEL_RESOLUTION;

    private final HashMap<Character, Double> charSet;

    private double maxBrightness;
    private double minBrightness;

    /**
     * Constructor for the SubImgCharMatcher class.
     * @param charSet The set of characters to be matched with sub-images.
     */
    public SubImgCharMatcher(char[] charSet) {
        this.charSet = new HashMap<>();
        for (char ch : charSet) {
            // Initialize the character set with null brightness values.
            this.charSet.put(ch, matchBrightness(ch));
        }
        this.normalizeBrightness();
    }

    /**
     * Returns the character with the closest brightness value (in absolute value) to the given brightness.
     * If there are multiple characters with the same brightness,
     * the character with the smallest ASCII value is returned.
     * @param brightness The brightness value to be matched with a character.
     * @return The character with the closest brightness value to the given brightness.
     */
    public char getCharByImageBrightness(double brightness) {
        char closestChar = ' ';
        // Set up variable to check the difference between two brightness values.
        double minDiff = Double.MAX_VALUE;
        // For each char in the set, check its difference from the desired brightness value.
        for (char ch : this.charSet.keySet()) {
            double diff = Math.abs(this.charSet.get(ch) - brightness);
            if (diff < minDiff) {
                minDiff = diff;
                closestChar = ch;
            } else if (diff == minDiff) {
                /*
                 If there are multiple characters with the same brightness,
                 the character with the smallest ASCII value is returned.
                 */
                closestChar = (ch < closestChar) ? ch : closestChar;
            }
        }
        return closestChar;
    }

    /**
     * Returns the character with the closest brightness value (in ceiling or floor) to the given brightness.
     * <p>If there are multiple characters with the same brightness,
     * the character with the smallest ASCII value is returned.</p>
     * @param brightness The brightness value to be matched with a character.
     * @param roundMethod The rounding method to be used.
     * @return The character with the closest brightness value to the given brightness.
     */
    public char getCharByImageBrightness(double brightness, RoundMethod roundMethod) {
        if (roundMethod == RoundMethod.ABSOLUTE) {
            return getCharByImageBrightness(brightness);
        }
        char closestChar = ' ';
        // Set up variable to check the difference between two brightness values.
        double minDiff = Double.MAX_VALUE;
        // For each char in the set, check its difference from the desired brightness value.
        for (char ch : this.charSet.keySet()) {
            double diff;
            if (roundMethod == RoundMethod.UP) {
                diff = Math.ceil(this.charSet.get(ch) - brightness);
            } else {
                diff = Math.floor(this.charSet.get(ch) - brightness);
            }
            if (diff < minDiff) {
                minDiff = diff;
                closestChar = ch;
            } else if (diff == minDiff) {
                /*
                 If there are multiple characters with the same brightness,
                 the character with the smallest ASCII value is returned.
                 */
                closestChar = (ch < closestChar) ? ch : closestChar;
            }
        }
        return closestChar;
    }

    /**
     * Adds a character to the character set.
     * @param c The character to add.
     */
    public void addChar(char c) {
        double brightness = matchBrightness(c);
        /*
         If the brightness is larger/smaller than the current min/max brightness,
         we need to normalize each character's brightness value in the set.
         */
        if (brightness < this.minBrightness || brightness > this.maxBrightness) {
            this.charSet.put(c, brightness);
            this.normalizeBrightness();
        } else {
            double normalizedBrightness = (brightness - this.minBrightness) /
                                          (this.maxBrightness - this.minBrightness);
            this.charSet.put(c, normalizedBrightness);
        }
    }

    /**
     * Removes a character from the character set if it is in the set.
     * @param c The character to remove.
     */
    public void removeChar(char c) {
        double brightness = charSet.get(c);
        this.charSet.remove(c);
        /*
         If the brightness is larger/smaller than the current min/max brightness,
         we need to normalize each character's brightness value in the set.
         */
        if (brightness < this.minBrightness || brightness > this.maxBrightness) {
            this.normalizeBrightness();
        }
    }

    /**
     * Count the number of true cells in a given boolean matrix.
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
     * Calculates the given character's brightness value.
     * @param c The character to match brightness to.
     * @return The brightness value of the character.
     */
    private static double matchBrightness(char c) {
        boolean[][] convertedChar = CharConverter.convertToBoolArray(c);
        int numTrue = countTrue(convertedChar);
        /*
        The brightness value for each character is the number of true cells in its matrix representation
        divided by the total number of pixels in its matrix representation.
        */
        return numTrue / (double) NUM_OF_PIXELS_IN_CONVERTED_CHARACTER;
    }

    /**
     * Normalizes the brightness values in a given character set.
     * Called only when all the characters in the set have an assigned brightness value.
     */
    private void normalizeBrightness() {
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

        this.maxBrightness = maxBrightness;
        this.minBrightness = minBrightness;

        // Normalize each character according to the min and max brightness.
        for (char c : charSet.keySet()) {
            double newBrightness = (charSet.get(c) - minBrightness) / (maxBrightness - minBrightness);
            // Because c is already a key in charSet, this will update its value
            charSet.put(c, newBrightness);
        }
    }

}
