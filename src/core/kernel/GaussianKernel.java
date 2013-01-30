package core.kernel;

import core.interfaces.KernelFunction;

/**
 * @author Jan Paw
 *         Date: 13.10.12
 */
public class GaussianKernel implements KernelFunction {
    @Override
    public double getKernelValue(double distanceToRoot, double smoothingLength) {
        return (1 / (smoothingLength * 3.1415926535897932384626433832795d)) * Math.exp(-Math.pow(distanceToRoot / smoothingLength, 2));
    }

    @Override
    public String toString() {
        return "GaussianKernel";
    }
}
