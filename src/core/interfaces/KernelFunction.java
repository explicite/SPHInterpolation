package core.interfaces;

/**
 * @author Jan Paw
 *         Date: 13.10.12
 */
public interface KernelFunction {
    double getKernelValue(double distanceToRoot, double smoothingLength);
}
