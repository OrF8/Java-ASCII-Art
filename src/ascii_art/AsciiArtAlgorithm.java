package ascii_art;

import image.Image;
import image.ImagePadder;
import image.SubImageHandler;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Responsible for a single ASCII-ART algorithm run.
 */
public class AsciiArtAlgorithm {

    // Static fields to store the current values for the next run.
    private static HashSet<Character> prevCharSet;
    private static SubImgCharMatcher prevCharMatcher;
    private static double[][] imageBrightnessValue;
    private static String prevImagePath;
    private static int prevResolution;
    private static Image prevPaddedImage;

    // Instance final fields.
    private final String imagePath;
    private final int resolution;

    // Instance fields.
    private SubImgCharMatcher charMatcher;

    /**
     * Creates a new AsciiArtAlgorithm instance.
     *
     * @param imagePath  The imagePath to create art from.
     * @param charSet    The set of characters to create the art with.
     * @param resolution The resolution of the output ASCII art imagePath.
     */
    public AsciiArtAlgorithm(String imagePath, HashSet<Character> charSet, int resolution) {
        char[] charSetArray = toCharArray(charSet);
        if (!Arrays.equals(charSetArray, toCharArray(prevCharSet))) { // If the character set has changed.
            updateCharMatcherAndSet(charSet, charSetArray);
        } else { // If the character set has not changed.
            this.charMatcher = prevCharMatcher;
        }

        this.imagePath = imagePath;
        this.resolution = resolution;

    }

    /**
     * Updates the character matcher and the character set.
     * @param charSet The new character set.
     * @param charSetArray The new character set as an array.
     */
    private void updateCharMatcherAndSet(HashSet<Character> charSet, char[] charSetArray) {
        if (prevCharMatcher == null) { // First instantiation.
            this.charMatcher = new SubImgCharMatcher(charSetArray);
            prevCharMatcher = this.charMatcher;
        } else { // Not the first instantiation.
            this.charMatcher = prevCharMatcher;
            /*
             We will first add all the new characters to the character matcher,
             and then remove all the characters that are not in the new character set.
             We do this because otherwise, the map in SubImgCharMatcher could have less than two
             characters, which would cause the algorithm to fail.
             */
            for (char c : charSet) { // Add all the new characters to the character matcher.
                if (!prevCharSet.contains(c)) {
                    this.charMatcher.addChar(c);
                }
            }
            for (char c : prevCharSet) { // Remove all the characters that are not in the new character set.
                if (!charSet.contains(c)) {
                    this.charMatcher.removeChar(c);
                }
            }
        }
        /*
         We need to update the previous character set to the new character set.
         We use new HashSet<>(charSet) to create a new HashSet with the same elements as charSet,
         but with a different reference.
         */
        prevCharSet = new HashSet<>(charSet);
    }

    /**
     * Converts a HashSet of characters to a character array.
     * @param charSet The HashSet of characters to convert.
     * @return The character array representation of the HashSet.
     */
    private char[] toCharArray(HashSet<Character> charSet) {
        if (charSet == null) {
            return null;
        }
        char[] arr = new char[charSet.size()];
        int i = 0;
        for (Character c : charSet) {
            arr[i] = c;
            i++;
        }
        return arr;
    }

    /**
     * Creates the ASCII output from scratch.
     * This method is called when the imagePath has changed or the resolution has changed.
     * @return A 2D character array
     * where each entry represents a character
     * that matches the brightness value of the entry in the original imagePath.
     */
    private char[][] createAsciiOutputFromScratch() {
        Image[][] subImages = SubImageHandler.divideImage(prevPaddedImage, resolution);
        int numRows = subImages.length;
        int numCols = subImages[0].length;
        char[][] asciiOutput = new char[numRows][numCols];

        // Since we changed the sub images, we need to create a new 2D array for the brightness values.
        imageBrightnessValue = new double[numRows][numCols];

        // For each sub-image, get the brightness value and the corresponding character.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                double subImageBrightness = SubImageHandler.getImageBrightness(subImages[row][col]);
                imageBrightnessValue[row][col] = subImageBrightness; // Save for future algorithm runs.
                asciiOutput[row][col] = charMatcher.getCharByImageBrightness(subImageBrightness);
            }
        }
        return asciiOutput;
    }

    /**
     * Creates the ASCII output from existing brightness values.
     * This method is called when the imagePath and resolution have not changed.
     * @return A 2D <code>char</code> array
     * where each entry represents a character
     * that matches the brightness value of the entry in the original imagePath.
     */
    private char[][] createAsciiOutputFromExistingBrightnessValues() {
        int numRows = imageBrightnessValue.length;
        int numCols = imageBrightnessValue[0].length;
        char[][] asciiOutput = new char[numRows][numCols];

        // For each sub-image, get the brightness value and the corresponding character.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                asciiOutput[row][col] = charMatcher.getCharByImageBrightness(imageBrightnessValue[row][col]);
            }
        }
        return asciiOutput;
    }

    /**
     * Runs the ASCII-ART algorithm.
     * @return A 2D <code>char</code> array where each entry represents
     * a character that matches the brightness value of the entry in the original imagePath.
     * @throws IOException In case the image path is invalid.
     */
    public char[][] run() throws IOException {
        char[][] asciiOutput;
        // If this is the first run (prevImagePath is null) or the imagePath has changed.
        if (prevImagePath == null || !prevImagePath.equals(imagePath)) {
            prevPaddedImage = ImagePadder.padImage(new Image(imagePath));
            asciiOutput =  createAsciiOutputFromScratch();
        } else {
            if (prevResolution != resolution) { // Resolution has changed, but the same image.
                asciiOutput = createAsciiOutputFromScratch();
            } else { // Same image and resolution.
                asciiOutput = createAsciiOutputFromExistingBrightnessValues();
            }
        }

        // Update the previous values to the current values.
        prevImagePath = imagePath;
        prevResolution = resolution;
        return asciiOutput;
    }

}
