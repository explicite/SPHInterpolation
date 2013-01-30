package image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Jan Paw
 *         Date: 26.06.12
 */
public final class ThresholdFilter {
    private final ArrayList elementData = new ArrayList();
    private int threshold;
    private BufferedImage image;

    public ThresholdFilter(int threshold, BufferedImage image) {
        this.image = image;
        this.threshold = threshold;
    }

    private boolean contains(Integer color) {
        for (Object anElementData : elementData) {
            if (isNearColor((Integer) anElementData, color))
                return true;
        }

        return false;
    }

    private boolean isNearColor(int firstRGB, int secondRGB) {
        int[] firstRGBElements = getRGBElements(firstRGB);
        int[] secondRGBElements = getRGBElements(secondRGB);
        for (int i = 0; i < 3; i++)
            if (Math.abs(firstRGBElements[i] - secondRGBElements[i]) > threshold)
                return false;

        return true;
    }

    private int[] getRGBElements(int rgb) {
        return new int[]{
                (rgb >> 16) & 0xff,
                (rgb >> 8) & 0xff,
                (rgb) & 0xff
        };
    }

    private void add(int color) {
        elementData.add(color);
    }

    private void createColorList() {
        int rgbColor;
        for (int width = 0; width < image.getWidth(); width++) {
            for (int height = 0; height < image.getHeight(); height++) {
                rgbColor = image.getRGB(width, height);
                if (!this.contains(rgbColor))
                    this.add(rgbColor);
            }
        }
    }

    private int getNearestColor(int color) {
        for (Object anElementData : elementData)
            if (isNearColor((Integer) anElementData, color))
                return (Integer) anElementData;

        return -1;
    }

    public BufferedImage getFilteredImage() {
        createColorList();
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int width = 0; width < image.getWidth(); width++) {
            for (int height = 0; height < image.getHeight(); height++) {
                filteredImage.setRGB(width, height, getNearestColor(image.getRGB(width, height)));
            }
        }

        return filteredImage;
    }
}
