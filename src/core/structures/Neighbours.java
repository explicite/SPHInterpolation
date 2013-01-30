package core.structures;

import core.interfaces.KernelFunction;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Jan Paw
 *         Date: 22.09.12
 */
public final class Neighbours implements Iterable<Node> {
    private Node rootNode;
    private ArrayList<Node> neighborhoods;
    private double smoothingLength;
    private KernelFunction kernelFunction;


    public Neighbours(KernelFunction kernelFunction) {
        this.rootNode = new Node(0d, 0d, 0d);
        this.neighborhoods = new ArrayList<Node>();
        this.smoothingLength = 0d;
        this.kernelFunction = kernelFunction;
    }

    public Neighbours(Node rootNode, KernelFunction kernelFunction) {
        this(kernelFunction);
        this.rootNode = rootNode;
    }

    public void addNeighborhood(Node neighbor) {
        if (!contains(neighbor)) {
            neighborhoods.add(neighbor);
/*
            double distance = neighbor.distance(rootNode);
            if (distance > smoothingLength)
                smoothingLength = distance;*/
        }
    }

    private void setNeighborhoodRadius() {
        double radius;
        for (Node node : neighborhoods) {
            radius = node.distance(rootNode);
            if (radius > smoothingLength)
                smoothingLength = radius;
        }
    }

    public double getSmoothingLength() {
        return smoothingLength;
    }

    public double distanceToRoot(Node node) {
        return rootNode.distance(node);
    }


    public void removeLast() {
        Node toRemove = rootNode;
        for (Node node : neighborhoods)
            if (distanceToRoot(node) > distanceToRoot(toRemove))
                toRemove = node;

        if (neighborhoods.contains(toRemove))
            remove(toRemove);

        setNeighborhoodRadius();
    }

    @Override
    public Iterator<Node> iterator() {
        return neighborhoods.iterator();
    }

    public int size() {
        return neighborhoods.size();
    }

    public double kernelValue(Node node) {
        return kernelFunction.getKernelValue(distanceToRoot(node), smoothingLength);
    }

    public boolean contains(Node node) {
        return (neighborhoods.contains(node) || rootNode == node);
    }

    private void remove(Node node) {
        neighborhoods.remove(node);
    }
}
