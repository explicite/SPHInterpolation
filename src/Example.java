import core.Interpolation;
import core.interfaces.KernelFunction;
import core.kernel.BSpline;
import core.kernel.GaussianKernel;
import core.kernel.QuinticSpline;
import fe.FEMesh;
import image.ImageManipulator;
import image.PictureWithScale;

import java.io.File;
import java.io.IOException;

/**
 * @author Jan Paw
 *         Date: 21.10.12
 */
public class Example {
    static File directory = new File(".");

    public static void main(String[] args) throws IOException {
        String path = directory.getCanonicalPath()+"/";
        String fileName = path + "rollin_65pc_2.png";

        ImageManipulator imageManipulator = new ImageManipulator(fileName);
        imageManipulator.autoCrop();
        /*imageManipulator.thresholdFilter(50);
        imageManipulator.saveImage("tresh");
*/
        FEMesh mesh = new FEMesh(path + "FE_results_mesh_.txt");
        imageManipulator.autoCrop();

        Interpolation interpolation = new Interpolation(imageManipulator.getTempImage(), mesh);
        KernelFunction[] kernelFunctions = {new QuinticSpline()};
        Integer[] numberOfNeighbours = {30};
        long start = System.currentTimeMillis();
        for (KernelFunction function : kernelFunctions) {
            for (Integer neighbours : numberOfNeighbours) {
                interpolation.interpolate(neighbours, function);
                PictureWithScale pictureWithScale = new PictureWithScale(path + "values" + function.toString() + neighbours + ".txt");
               // pictureWithScale.drawOnImage(path + function.toString() + "_neighbours_" + neighbours + ".png");
                System.out.println(neighbours + " " + (System.currentTimeMillis() - start));
            }
        }
    }
}
