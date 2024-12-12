package image_char_matching;

import java.util.HashMap;

/**
 * Responsible for matching an ASCII character to a sub-image with a given brightness.
 * Will be used for the ASCII-Art algorithm to replace sub-images with characters.
 */
public class SubImgCharMatcher {

    private final HashMap<Character, Double> charSet;

    /**
     * Constructor for the SubImgCharMatcher class.
     * @param charset The set of characters to be matched with sub-images.
     */
    public SubImgCharMatcher(char[] charset) {
        this.charSet = new HashMap<>();
        for (char ch : charset) {
            // Initialize the character set with null brightness values.
            this.charSet.put(ch, null);
        }
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
                 * If there are multiple characters with the same brightness,
                 * the character with the smallest ASCII value is returned.
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
        this.charSet.put(c, null);
    }

    /**
     * Removes a character from the character set if it is in the set.
     * @param c The character to remove.
     */
    public void removeChar(char c) {
        this.charSet.remove(c);
    }
}
