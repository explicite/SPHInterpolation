package core;


import core.concurrent.Parallel;
import core.interfaces.KernelFunction;
import core.structures.Neighbours;
import core.structures.Node;
import core.structures.Space;
import fe.FEMesh;
import image.ImageManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * @author Jan Paw
 *         Date: 13.10.12
 */
public class Interpolation {
    private BufferedImage structure;
    private volatile Space feNodes;
    private volatile Space caNodes;

    public Interpolation(BufferedImage structure, FEMesh mesh) {
        this.structure = structure;
        ImageManipulator imageImageManipulator = new ImageManipulator(structure);
        imageImageManipulator.autoCrop();
        this.structure = imageImageManipulator.getTempImage();
        mesh.scale(structure.getWidth(), structure.getHeight());

        this.feNodes = new Space(mesh.getArrayOfNodes());
        this.caNodes = new Space(getCANodes());
    }

    public void interpolate(int minNumberOfNeighbours, KernelFunction kernelFunction) {
        feNodes.setKernelFunction(kernelFunction);
        feNodes.setMinNumberOfNeighbours(minNumberOfNeighbours);
        feNodes.setAverageRadiusOfTheNeighborhood(caNodes.size() / feNodes.size());

        caNodes.setKernelFunction(kernelFunction);
        caNodes.setMinNumberOfNeighbours(minNumberOfNeighbours);
        caNodes.setAverageRadiusOfTheNeighborhood(1d);

        setCaVolume();
        setCaValue();
    }


    private void setCaVolume() {
        Parallel.For(feNodes, new Parallel.Operation<Node>() {
            @Override
            public void perform(Node node) {
                node.setVolume(feNodes.getVolumeOfParticle(node));
            }
        });
    }

    private void setCaValue() {
        FileWriter outFile = null;
        try {
            File directory = new File(".");
            String path = directory.getCanonicalPath() + "/";
            outFile = new FileWriter(path + "interpolation.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final PrintWriter out = new PrintWriter(outFile);


        Parallel.For(caNodes, new Parallel.Operation<Node>() {
            @Override
            public void perform(Node node) {
                Neighbours neighbours = feNodes.findNeighbors(node);

                double bCorrection = 0d;
                for (Node neighbour : neighbours) {
                    bCorrection += neighbours.kernelValue(neighbour) * neighbour.getVolume();
                }

                double value = 0d;
                for (Node neighbour : neighbours) {
                    value += neighbour.getValue() * (neighbours.kernelValue(neighbour) / bCorrection) * neighbour.getVolume();
                }
                out.println((int) node.getX() + " " + (int) node.getY() + " " + value);
            }
        });
        out.close();
    }

    private ArrayList<Node> getCANodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int x = 0; x < structure.getWidth(); x++)
            for (int y = 0; y < structure.getHeight(); y++)
                if (structure.getRGB(x, y) != Color.WHITE.getRGB())
                    nodes.add(new Node(x, y));
        return nodes;
    }


    private double[] findBounds(BufferedReader in) throws IOException {
        double[] bounds = new double[2];
        String line = in.readLine();
        String[] splitLine = line.split(" ");
        bounds[0] = Double.valueOf(splitLine[2]);
        bounds[1] = Double.valueOf(splitLine[2]);

        while ((line = in.readLine()) != null) {
            splitLine = line.split(" ");
            if (Double.valueOf(splitLine[2]) < bounds[0])
                bounds[0] = Double.valueOf(splitLine[2]);
            else if (Double.valueOf(splitLine[2]) > bounds[1]) {
                bounds[1] = Double.valueOf(splitLine[2]);
            }
        }

        return bounds;
    }

}
