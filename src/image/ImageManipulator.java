package image;

import fe.FEMesh;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Jan Paw
 *         Date: 26.06.12
 */
public class ImageManipulator {
    private BufferedImage originalImage;
    private BufferedImage tempImage;
    private int topBound;
    private int bottomBound;
    private int leftBound;
    private int rightBound;

    private boolean cropped;


    public ImageManipulator(String fileName) {
        try {
            originalImage = ImageIO.read(new File(fileName));
            tempImage = originalImage;
            topBound = 0;
            bottomBound = tempImage.getHeight() - 1;
            leftBound = 0;
            rightBound = tempImage.getWidth() - 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageManipulator(BufferedImage structure) {
        originalImage = structure;
        tempImage = structure;
        topBound = 0;
        bottomBound = tempImage.getHeight() - 1;
        leftBound = 0;
        rightBound = tempImage.getWidth() - 1;
    }

    public void saveImage(String name) {
        try {
            ImageIO.write(tempImage, "png", new File(name+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void thresholdFilter(int threshold) {
        ThresholdFilter thresholdFilter = new ThresholdFilter(threshold, originalImage);
        tempImage = thresholdFilter.getFilteredImage();
    }

    public BufferedImage getTempImage() {
        return tempImage;
    }

    public void autoCrop() {
        if (!cropped) {
            findBounds();
            tempImage = tempImage.getSubimage(leftBound, topBound, rightBound - leftBound + 1, bottomBound - topBound + 1);
            cropped = true;
        }
    }

    public void printFEMesh(FEMesh nodes) {
        if (!cropped) {
            autoCrop();
        }
        nodes.scale(tempImage.getWidth(), tempImage.getHeight());

        for (int i = 0; i < nodes.SIZE; i++)
            tempImage.setRGB((int) (nodes.getNode(i).getX() - 0.1), (int) (nodes.getNode(i).getY() - 0.1), -16777216);
    }


    private void findBounds() {
        if (tempImage.getWidth() >= tempImage.getHeight()) {
            findTop();
            findBottom();
            findLeft();
            findRight();
        } else {
            findLeft();
            findRight();
            findTop();
            findBottom();
        }
    }

    private void findTop() {
        while (!isTop(topBound))
            topBound++;
    }

    private void findBottom() {
        while (!isBottom(bottomBound)) {
            bottomBound--;
        }
    }

    private void findLeft() {
        while (!isLeft(leftBound)) {
            leftBound++;
        }
    }

    private void findRight() {
        while (!isRight(rightBound)) {
            rightBound--;
        }
    }

    private boolean isVertical(int bound) {
        for (int i = topBound; i < bottomBound; i++)
            if (tempImage.getRGB(bound, i) != Color.WHITE.getRGB())
                return true;
        return false;
    }

    private boolean isHorizontal(int bound) {
        for (int i = leftBound; i < rightBound; i++)
            if (tempImage.getRGB(i, bound) != Color.WHITE.getRGB())
                return true;
        return false;
    }

    private boolean isTop(int bound) {
        return isHorizontal(bound);
    }

    private boolean isBottom(int bound) {
        return isHorizontal(bound);
    }

    private boolean isLeft(int bound) {
        return isVertical(bound);
    }

    private boolean isRight(int bound) {
        return isVertical(bound);
    }
}
