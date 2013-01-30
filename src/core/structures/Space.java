package core.structures;

import core.interfaces.KernelFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jan Paw
 *         Date: 13.10.12
 */
public final class Space implements Iterable<Node> {
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private KernelFunction kernelFunction;
    private int minNumberOfNeighbours;
    private double averageRadiusOfTheNeighborhood;

    public Space() {
        this.nodes = new ArrayList<Node>();
        this.minNumberOfNeighbours = 0;
        this.averageRadiusOfTheNeighborhood = 0d;
    }

    public Space(ArrayList<Node> nodes) {
        this();
        this.nodes = nodes;
    }

    public Neighbours findNeighbors(Node rootNode) {
        Neighbours neighbours = new Neighbours(rootNode, kernelFunction);
        double searchRadius = Double.MIN_VALUE;

        do {
            for (Node node : nodes)
                if (!neighbours.contains(node) && node.distance(rootNode) <= searchRadius)
                    neighbours.addNeighborhood(node);

            searchRadius += 1;
        } while (neighbours.size() <= minNumberOfNeighbours);

        return limitNeighbours(neighbours);
    }

    public double getVolumeOfParticle(Node rootNode) {
        double kernelValue = 0d;
        Neighbours neighbours = findNeighbors(rootNode);

        for (Node node : neighbours)
            kernelValue += neighbours.kernelValue(node);

        return (1d / kernelValue);
    }

    public void add(Node node) {
        nodes.add(node);
    }

    private Neighbours limitNeighbours(Neighbours neighbours) {
        while (neighbours.size() > minNumberOfNeighbours)
            neighbours.removeLast();

        return neighbours;
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

    public int getMinNumberOfNeighbours() {
        return minNumberOfNeighbours;
    }

    public void setMinNumberOfNeighbours(int minNumberOfNeighbours) {
        this.minNumberOfNeighbours = minNumberOfNeighbours;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public KernelFunction getKernelFunction() {
        return kernelFunction;
    }

    public void setKernelFunction(KernelFunction kernelFunction) {
        this.kernelFunction = kernelFunction;
    }

    public int size() {
        return nodes.size();
    }

    public List<Node> getList() {
        return nodes;
    }

    public double getAverageRadiusOfTheNeighborhood() {
        return averageRadiusOfTheNeighborhood;
    }

    public void setAverageRadiusOfTheNeighborhood(double averageRadiusOfTheNeighborhood) {
        this.averageRadiusOfTheNeighborhood = averageRadiusOfTheNeighborhood;
    }
}
