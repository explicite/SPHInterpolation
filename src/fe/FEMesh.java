package fe;

import core.structures.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Jan Paw
 *         Date: 21.07.12
 */
public final class FEMesh {
    private Node[] nodes;
    public int SIZE;
    private double minimalX = 0.0;
    private double minimalY = 0.0;
    private double maximalX = 0.0;
    private double maximalY = 0.0;
    ArrayList<Node> arrayOfNodes = new ArrayList<Node>();

    public FEMesh(Node[] nodes) {
        this.SIZE = nodes.length;
        this.nodes = nodes;
        findBoundaryNodes();
    }

    public FEMesh(String fileName) {
        try {
            BufferedReader in = new BufferedReader((new FileReader(fileName)));

            arrayOfNodes = new ArrayList<Node>();
            String line;
            int i = 0;
            while ((line = in.readLine()) != null) {
                String[] splitLine = line.split(" ");

                if (Double.valueOf(splitLine[0]) <= 300) {
                    arrayOfNodes.add(new Node(Double.valueOf(splitLine[0]), Double.valueOf(splitLine[1]), Double.valueOf(splitLine[2]), 0));


                    if (i == 0) {
                        minimalX = arrayOfNodes.get(0).getX();
                        maximalX = minimalX;
                        minimalY = arrayOfNodes.get(0).getY();
                        maximalY = minimalY;
                    }

                    if (arrayOfNodes.get(i).getX() > maximalX)
                        maximalX = arrayOfNodes.get(i).getX();
                    else if (arrayOfNodes.get(i).getX() < minimalX)
                        minimalX = arrayOfNodes.get(i).getX();

                    if (arrayOfNodes.get(i).getY() > maximalY)
                        maximalY = arrayOfNodes.get(i).getY();
                    else if (arrayOfNodes.get(i).getY() < minimalY)
                        minimalY = arrayOfNodes.get(i).getY();

                    i++;
                }
            }

            nodes = new Node[arrayOfNodes.size()];
            nodes = arrayOfNodes.toArray(nodes);
            SIZE = nodes.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNode(int index, Node node) {
        nodes[index] = node;
    }

    public Node getNode(int index) {
        return nodes[index];
    }

    public ArrayList<Node> getArrayOfNodes() {
        return arrayOfNodes;
    }

    public void scale(double weight, double height) {
        double scaleX;
        double scaleY;

        if (minimalX != 0 || minimalY != 0)
            moveNodesToOrigin();

        scaleX = weight / maximalX;
        scaleY = height / maximalY;

        maximalX *= scaleX;
        maximalY *= scaleY;

        for (Node node : nodes) {
            node.scaleX(scaleX);
            node.scaleY(scaleY);
        }
    }

    private void findBoundaryNodes() {
        minimalX = nodes[0].getX();
        maximalX = minimalX;
        minimalY = nodes[0].getY();
        maximalY = minimalY;

        for (int i = 1; i > SIZE; i++) {
            if (nodes[i].getX() > maximalX)
                maximalX = nodes[i].getX();
            else if (nodes[i].getX() < minimalX)
                minimalX = nodes[i].getX();

            if (nodes[i].getY() > maximalY)
                maximalY = nodes[i].getY();
            else if (nodes[i].getY() < minimalY)
                minimalY = nodes[i].getY();
        }
    }

    private void moveNodesToOrigin() {
        double moveX = -minimalX;
        double moveY = -minimalY;
        minimalY = 0;
        minimalX = 0;

        maximalX += moveX;
        maximalY += moveY;

        for (Node node : nodes) {
            if (moveX != 0)
                node.transformX(moveX);
            if (moveY != 0)
                node.transformY(moveY);
        }
    }
}
