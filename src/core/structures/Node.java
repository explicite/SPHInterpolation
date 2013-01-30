package core.structures;

import java.awt.*;

/**
 * @author Jan Paw
 *         Date: 21.07.12
 */
public final class Node {
    private double x;
    private double y;
    private double value;
    private double volume;

    public Node(double x, double y, double value, double volume) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.volume = volume;
    }

    public Node(double x, double y, double volume) {
        this.x = x;
        this.y = y;
        this.volume = volume;
        this.value = 0d;
    }

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
        this.value = 0d;
        this.volume = 0d;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getValue() {
        return value;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void transformX(double transform) {
        this.x += transform;
    }

    public void transformY(double transform) {
        this.y += transform;
    }

    public void scaleX(double scale) {
        this.x *= scale;
    }

    public void scaleY(double scale) {
        this.y *= scale;
    }

    public double distance(Node node) {
        return Math.sqrt(((node.x - this.x) * (node.x - this.x)) + ((node.y - this.y) * (node.y - this.y)));
    }

    public double distance(Point point) {
        return Math.sqrt(((point.getX() - this.x) * (point.getX() - this.x)) + ((point.getY() - this.y) * (point.getY() - this.y)));
    }

    public boolean equals(Node node) {
        if (node.getX() == x && node.getY() == y && node.getValue() == value)
            return true;
        else
            return false;
    }

    public boolean samePosition(Node node) {
        return node.getX() == x && node.getY() == y;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
