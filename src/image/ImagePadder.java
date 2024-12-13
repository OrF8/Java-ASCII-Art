package image;

import utils.MathUtils;

import java.awt.*;

/**
 * A utility class responsible for padding a given image s.t its dimensions will be powers of 2.
 */
public class ImagePadder {

    private static final int TWO_TO_THE_POWER_OF_1 = 2;

    /**
     * A private constructor to prevent instantiation of this class.
     */
    private ImagePadder() {
        // This class should not be instantiated.
    }


    /**
     * Pads the given image with white pixels such that its dimensions will be powers of 2.
     * @param image The image to pad.
     * @return The padded image.
     */
    public static Image padImage(Image image) {
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        int heightAfterPadding = MathUtils.closestPowerOfTwo(imageHeight);
        int widthAfterPadding = MathUtils.closestPowerOfTwo(imageWidth);
        int heightDiff = heightAfterPadding - imageHeight;
        int widthDiff = widthAfterPadding - imageWidth;

        if (heightDiff == 0 && widthDiff == 0) { // If the image dimensions are already powers of two
            return image;
        }

        Color[][] paddedImage = new Color[heightAfterPadding][widthAfterPadding];
        for (int i = 0; i < heightAfterPadding; i++) {
            for (int j = 0; j < widthAfterPadding; j++) {
                // If the current pixel is outside the original image, set it to white.
                if (i < heightDiff / TWO_TO_THE_POWER_OF_1) {
                    paddedImage[i][j] = Color.WHITE;
                } else if (j < widthDiff / TWO_TO_THE_POWER_OF_1) {
                    paddedImage[i][j] = Color.WHITE;
                } else if (i >= imageHeight + (heightDiff / TWO_TO_THE_POWER_OF_1)){
                    paddedImage[i][j] = Color.WHITE;
                } else if (j >= imageWidth + (widthDiff / TWO_TO_THE_POWER_OF_1)) {
                        paddedImage[i][j] = Color.WHITE;
                } else {
                    paddedImage[i][j] = image.getPixel(
                            i - (heightDiff / TWO_TO_THE_POWER_OF_1), j - (widthDiff / TWO_TO_THE_POWER_OF_1)
                    );
                }
            }
        }

        return new Image(paddedImage, widthAfterPadding, heightAfterPadding);
    }

}
