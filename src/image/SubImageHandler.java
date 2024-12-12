package image;

import java.awt.*;

/**
 * A utility class that handles all sub-images related functions.
 */
public class SubImageHandler {

    private static final float RED_TO_GREY_FACTOR = 0.2126f;
    private static final float GREEN_TO_GREY_FACTOR = 0.7152f;
    private static final float BLUE_TO_GREY_FACTOR = 0.0722f;
    private static final int MAX_RGB_VALUE = 255;

    private SubImageHandler() {
        // This class should not be instantiated.
    }

    /**
     * TODO: Go over this again
     * Divides a given image to sub-images of size resolution²
     * @param image The image.
     * @param resolution The resolution.
     * @return An ArrayList with all the sub-images of size resolution²
     */
    public static Image[][] divideImage(Image image, int resolution) {
        int imageHeight = image.getHeight();
        /*
         There are (resolution) images in a row, and since each one is a square,
         we need (image.getHeight() / resolution) rows.
         */
        int numRows = imageHeight / resolution;
        int numCols = image.getWidth() / resolution;

        int z = imageHeight / (image.getWidth() / resolution);
        Image[][] subImages = new Image[z][resolution];

        for (int row = 0; row < z; row++) {
            for (int col = 0; col < resolution; col++) {

                Color[][] subPixelArray = new Color[numRows][numCols];

                // Iterate resolution² times to create sub-images of size resolution²
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        // Calculate the desired pixel based on our current row and column
                        int x = row * numRows + j;
                        int y = col * numCols + i;
                        subPixelArray[i][j] = image.getPixel(x, y);
                    }
                }

                subImages[row][col] = new Image(subPixelArray, numRows, numCols);
            }
        }

        return subImages;
    }

    /**
     * Returns a given image's normalized brightness value.
     * @param image The image.
     * @return The image's normalized brightness value.
     */
    public static double getImageBrightness(Image image) {
        double greySum = 0;
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                Color pixel = image.getPixel(i, j);
                // Sum the grey value of all pixels in the image
                greySum += pixel.getRed() * RED_TO_GREY_FACTOR +
                           pixel.getGreen() * GREEN_TO_GREY_FACTOR +
                           pixel.getBlue() * BLUE_TO_GREY_FACTOR;
            }
        }
        double brightness = greySum / (imageHeight * imageWidth);
        return brightness / MAX_RGB_VALUE; // Return the normalized brightness
    }

}
