package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Jan Paw
 *         Date: 14.10.12
 */
public class PictureWithScale {
    private String dataFileName;
    private BufferedImage picture;
    public PictureWithScale(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    private double[] findBounds(BufferedReader in) throws IOException {
        double[] bounds = new double[6];
        String line = in.readLine();
        String[] splitLine = line.split(" ");
        bounds[0] = Double.valueOf(splitLine[2]);
        bounds[1] = Double.valueOf(splitLine[2]);
        bounds[2] = Double.valueOf(splitLine[0]);
        bounds[3] = Double.valueOf(splitLine[1]);
        bounds[4] = Double.valueOf(splitLine[0]);
        bounds[5] = Double.valueOf(splitLine[1]);


        while ((line = in.readLine()) != null) {
            splitLine = line.split(" ");

            if (Double.valueOf(splitLine[0]) < bounds[2])
                bounds[2] = Double.valueOf(splitLine[0]);
            else if (Double.valueOf(splitLine[0]) > bounds[4]) {
                bounds[4] = Double.valueOf(splitLine[0]);
            }

            if (Double.valueOf(splitLine[1]) < bounds[3])
                bounds[3] = Double.valueOf(splitLine[1]);
            else if (Double.valueOf(splitLine[1]) > bounds[5]) {
                bounds[5] = Double.valueOf(splitLine[1]);
            }

            if (Double.valueOf(splitLine[2]) < bounds[0])
                bounds[0] = Double.valueOf(splitLine[2]);
            else if (Double.valueOf(splitLine[2]) > bounds[1]) {
                bounds[1] = Double.valueOf(splitLine[2]);
            }
        }
        return bounds;
    }

    private double[] toRGB(double h, double s, double v) {
        int i;
        double f, p, q, t;
        double[] rgb = new double[3];
        h /= 60;
        i = (int) Math.floor(h);

        f = h - i;
        p = v * (1 - s);
        q = v * (1 - s * f);
        t = v * (1 - s * (1 - f));
        switch (i) {
            case 0:
                rgb[0] = v;
                rgb[1] = t;
                rgb[2] = p;
                break;
            case 1:
                rgb[0] = q;
                rgb[1] = v;
                rgb[2] = p;
                break;
            case 2:
                rgb[0] = p;
                rgb[1] = v;
                rgb[2] = t;
                break;
            case 3:
                rgb[0] = p;
                rgb[1] = q;
                rgb[2] = v;
                break;
            case 4:
                rgb[0] = t;
                rgb[1] = p;
                rgb[2] = v;
                break;
            default:
                rgb[0] = v;
                rgb[1] = p;
                rgb[2] = q;
                break;
        }
        return rgb;
    }

/*    public void drawOnImage(String imageFileName) {
        try {
            BufferedReader in = new BufferedReader((new FileReader(dataFileName)));
            double[] bounds = findBounds(in);
            double min = 0;
            double max = 1.9;
            BufferedImage img = new BufferedImage((int) Math.ceil(bounds[4] - bounds[2] + 1), (int) Math.ceil(bounds[5] - bounds[3] + 1), 6);
            String line;
            in = new BufferedReader((new FileReader(dataFileName)));
            ArrayList<Point> horizontalPoints = new ArrayList<Point>();
            ArrayList<Point> verticalPoints = new ArrayList<Point>();
            while ((line = in.readLine()) != null) {
                String[] splitLine = line.split(" ");
                double[] rgb = toRGB((360.d / (max - min) * Double.valueOf(splitLine[2])), 1.0, 1.0);
                img.setRGB(Integer.valueOf(splitLine[0]), Integer.valueOf(splitLine[1]), new Color((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255)).getRGB());


                if (Integer.valueOf(splitLine[0]) == (bounds[4] - bounds[2] + 1) / 2) {
                    verticalPoints.add(new Point(Integer.valueOf(splitLine[1]), (int) Math.round(100 - (100 / max * Double.valueOf(splitLine[2])))));
                }

                if (Integer.valueOf(splitLine[1]) == (bounds[5] - bounds[3] + 1) / 2) {
                    horizontalPoints.add(new Point(Integer.valueOf(splitLine[0]), (int) Math.round(100 - (100 / max * Double.valueOf(splitLine[2])))));
                }
            }

            in.close();
            img = drawCharts(img, verticalPoints, horizontalPoints);
            img = drawScale(img);
            img = drawDescription(img, min, max);

            picture = img;
            ImageIO.write(img, "png", new File(imageFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    public void drawOnImage() {
        try {
            BufferedReader in = new BufferedReader((new FileReader(dataFileName)));
            double[] bounds = findBounds(in);
            double min = 0;
            double max = 1.9;
            BufferedImage img = new BufferedImage((int) Math.ceil(bounds[4] - bounds[2] + 1), (int) Math.ceil(bounds[5] - bounds[3] + 1), 6);
            String line;
            in = new BufferedReader((new FileReader(dataFileName)));
            ArrayList<Point> horizontalPoints = new ArrayList<Point>();
            ArrayList<Point> verticalPoints = new ArrayList<Point>();
            while ((line = in.readLine()) != null) {
                String[] splitLine = line.split(" ");
                double[] rgb = toRGB((360.d / (max - min) * Double.valueOf(splitLine[2])), 1.0, 1.0);
                img.setRGB(Integer.valueOf(splitLine[0]), Integer.valueOf(splitLine[1]), new Color((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255)).getRGB());


                if (Integer.valueOf(splitLine[0]) == (bounds[4] - bounds[2] + 1) / 2) {
                    verticalPoints.add(new Point(Integer.valueOf(splitLine[1]), (int) Math.round(100 - (100 / max * Double.valueOf(splitLine[2])))));
                }

                if (Integer.valueOf(splitLine[1]) == (bounds[5] - bounds[3] + 1) / 2) {
                    horizontalPoints.add(new Point(Integer.valueOf(splitLine[0]), (int) Math.round(100 - (100 / max * Double.valueOf(splitLine[2])))));
                }
            }

            in.close();
            img = drawCharts(img, verticalPoints, horizontalPoints);
            img = drawScale(img);
            img = drawDescription(img, min, max);

            picture = img;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage drawCharts(BufferedImage image, ArrayList<Point> verticalPoints, ArrayList<Point> horizontalPoints) {


        BufferedImage imageWithCharts = new BufferedImage(image.getWidth() + 125, image.getHeight() + 150, image.getType());

        BufferedImage verticalChart = new BufferedImage(125, image.getHeight(), image.getType());
        for (Point point : verticalPoints) {
            verticalChart.setRGB(point.y, point.x, Color.BLACK.getRGB());
        }

        BufferedImage horizontalChart = new BufferedImage(image.getWidth(), 125, image.getType());
        for (Point point : horizontalPoints) {
            horizontalChart.setRGB(point.x, point.y, Color.BLACK.getRGB());
        }


        imageWithCharts.getGraphics().drawImage(image, 0, 0, Math.min(imageWithCharts.getWidth(), image.getWidth()),
                Math.min(imageWithCharts.getHeight(), image.getHeight()), null);

        imageWithCharts.getGraphics().drawImage(horizontalChart, 0, image.getHeight(),
                Math.min(imageWithCharts.getWidth(), horizontalChart.getWidth()),
                Math.min(imageWithCharts.getHeight(), horizontalChart.getHeight()), null);

        imageWithCharts.getGraphics().drawImage(verticalChart, image.getWidth(), 0,
                Math.min(imageWithCharts.getWidth(), verticalChart.getWidth()),
                Math.min(imageWithCharts.getHeight(), verticalChart.getHeight()), null);

        return imageWithCharts;
    }

    private BufferedImage drawScale(BufferedImage image) {
        int length = 255;

        if (image.getHeight() < length)
            length = image.getHeight();

        BufferedImage scale = new BufferedImage(50, length, image.getType());


        for (int i = 0; i < length; i++) {
            double[] rgb = toRGB(360.d / length * i, 1d, 1d);
            for (int j = 0; j < 20; j++) {
                scale.setRGB(j, i, new Color((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255)).getRGB());
            }
        }

        BufferedImage imageWithScale = new BufferedImage(image.getWidth() + scale.getWidth() + 100, image.getHeight(), image.getType());

        imageWithScale.getGraphics().drawImage(image, 0, 0, Math.min(imageWithScale.getWidth(), image.getWidth()),
                Math.min(imageWithScale.getHeight(), image.getHeight()), null);

        imageWithScale.getGraphics().drawImage(scale, imageWithScale.getWidth() - scale.getWidth(), 0, Math.min(imageWithScale.getWidth(), scale.getWidth()),
                Math.min(imageWithScale.getHeight(), scale.getHeight()), null);

        return imageWithScale;
    }

    private BufferedImage drawDescription(BufferedImage image, double min, double max) {
        int length = 255;

        if (image.getHeight() < length)
            length = image.getHeight();

        DecimalFormat decimalFormat = new DecimalFormat("#0.00000000");

        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(Font.SANS_SERIF, -1, 12));
        graphics.drawString(decimalFormat.format(min), image.getWidth() - 130, 9);
        graphics.drawString(decimalFormat.format(max * 0.25), image.getWidth() - 130, (int) (length * 0.25));
        graphics.drawString(decimalFormat.format(max * 0.5), image.getWidth() - 130, (int) (length * 0.5));
        graphics.drawString(decimalFormat.format(max * 0.75), image.getWidth() - 130, (int) (length * 0.75));
        graphics.drawString(decimalFormat.format(max), image.getWidth() - 130, length);
        graphics.dispose();

        return image;
    }

    public BufferedImage getPicture() {
        return picture;
    }
}
