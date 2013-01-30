package core.kernel;

import core.interfaces.KernelFunction;

/**
 * @author Jan Paw
 *         Date: 13.10.12
 */
public class BSpline implements KernelFunction {
    @Override
    public double getKernelValue(double distanceToRoot, double smoothingLength) {
        double r = distanceToRoot / smoothingLength;
        double alpha = (2.142857142857143d) / Math.PI / smoothingLength / smoothingLength;

        if (r >= 0d && r < 1d)
            return alpha * (0.6666666666666667d - Math.pow(r, 2) + (0.5d * Math.pow(r, 3)));
        else if (r >= 1d && r < 2d)
            return alpha * (0.1666666666666667d * Math.pow(2d - r, 3));

        return 0;
    }

    @Override
    public String toString() {
        return "BSpline";
    }
}
