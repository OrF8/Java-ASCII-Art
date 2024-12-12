package ascii_art;

import image.Image;
import image.ImagePadder;
import image.SubImageHandler;
import image_char_matching.SubImgCharMatcher;

/**
 * Responsible for a single ASCII-ART algorithm run.
 */
public class AsciiArtAlgorithm {

    private final SubImgCharMatcher charMatcher;
    private final Image paddedImage;
    private final int resolution;

    /**
     * Creates a new AsciiArtAlgorithm instance.
     * @param image The image to create art from.
     * @param charSet The set of characters to create the art with.
     * @param resolution The resolution of the output ASCII art image.
     */
    public AsciiArtAlgorithm(Image image, char[] charSet, int resolution) {
        this.charMatcher = new SubImgCharMatcher(charSet);
        this.paddedImage = ImagePadder.padImage(image);
        this.resolution = resolution;
    }

    /**
     * Runs the ASCII-ART algorithm.
     * @return A 2D character array where each entry represents
     * a character that matches the brightness value of the entry in the original image.
     */
    public char[][] run() {
        Image[][] subImages = SubImageHandler.divideImage(paddedImage, resolution);

        char[][] asciiOutput = new char[subImages.length][subImages[0].length];

        for (int row = 0; row < subImages.length; row++) {
            for (int col = 0; col < subImages[row].length; col++) {
                double subImageBrightness = SubImageHandler.getImageBrightness(subImages[row][col]);
                asciiOutput[row][col] = charMatcher.getCharByImageBrightness(subImageBrightness);
            }
        }

        return asciiOutput;
    }

}
