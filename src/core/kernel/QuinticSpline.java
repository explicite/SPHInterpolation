package core.kernel;

import core.interfaces.KernelFunction;

/**
 * @author Jan Paw
 *         Date: 13.10.12
 */
public class QuinticSpline implements KernelFunction {
    @Override
    public double getKernelValue(double distanceToRoot, double smoothingLength) {
        double r = distanceToRoot / smoothingLength;
        double alpha = (0.014644351d) / Math.PI / smoothingLength / smoothingLength;

        if (r >= 0d && r < 1d)
            return alpha * (Math.pow(3d - r, 5) - (6d * Math.pow(2d - r, 5)) + (15d * Math.pow(1d - r, 5)));
        else if (r >= 1d && r < 2d)
            return alpha * (Math.pow(3d - r, 5) - (6d * Math.pow(2d - r, 5)));
        else if (r >= 2d && r < 3d)
            return alpha * (Math.pow(3d - r, 5));


        return 0;
    }

    @Override
    public String toString() {
        return "QuinticSpline";
    }
}
