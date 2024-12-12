package image;

import java.awt.*;

/**
 * A utility class responsible for padding a given image s.t its dimensions will be powers of 2.
 */
public class ImagePadder {

    private static final int TWO_TO_THE_POWER_OF_0 = 1;
    private static final int TWO_TO_THE_POWER_OF_1 = 2;
    private static final int TWO_TO_THE_POWER_OF_2 = 4;
    private static final int TWO_TO_THE_POWER_OF_3 = 8;
    private static final int TWO_TO_THE_POWER_OF_4 = 16;

    private ImagePadder() {
        // This class should not be instantiated.
    }

    /**
     * Returns the closest power of 2 to the given number.
     * @param n The number to find the closest power of 2 to.
     * @return The closest power of 2 to the given number.
     */
    private static int closestPowerOfTwo(int n) {
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

    /**
     * Pads the given image s.t its dimensions will be powers of 2.
     * @param image The image to pad.
     * @return The padded image.
     */
    public static Image padImage(Image image) {
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        int heightAfterPadding = closestPowerOfTwo(imageHeight);
        int widthAfterPadding = closestPowerOfTwo(imageWidth);
        int heightDiff = heightAfterPadding - imageHeight;
        int widthDiff = widthAfterPadding - imageWidth;

        if (heightDiff == 0 && widthDiff == 0) { // If the image dimensions are already powers of two
            return image;
        }

        Color[][] paddedImage = new Color[heightAfterPadding][widthAfterPadding];
        for (int i = 0; i < heightAfterPadding; i++) {
            for (int j = 0; j < widthAfterPadding; j++) {
                // If the current pixel is outside the original image, set it to white.
                if (i < heightDiff / 2 || j < widthDiff / 2) {
                    paddedImage[i][j] = Color.WHITE;
                } else if (i > imageHeight + (heightDiff / 2) || j > imageWidth + (widthDiff / 2)) {
                    paddedImage[i][j] = Color.WHITE;
                } else { // heightDiff / 2 <= i <= imageHeight + (heightDiff / 2)
                    if (i < heightDiff / 2 || j < widthDiff / 2 || i >= imageHeight + (heightDiff / 2) || j >= imageWidth + (widthDiff / 2)) {
                        paddedImage[i][j] = Color.WHITE;
                    } else {
                        paddedImage[i][j] = image.getPixel(i - (heightDiff / 2), j - (widthDiff / 2));
                    }
                }
            }
        }

        return new Image(paddedImage, widthAfterPadding, heightAfterPadding);
    }

}
