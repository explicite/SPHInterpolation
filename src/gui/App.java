package gui;

import core.Interpolation;
import fe.FEMesh;
import image.ImageManipulator;
import image.PictureWithScale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @author Jan Paw
 *         Date: 25.11.12
 */
public class App extends JFrame {
    private static JMenuBar menuBar;
    private static JMenuItem menu;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JProgressBar progressBar;
    private JPanel image;
    private CreateSimulation createSimulation;

    public App(String title) throws HeadlessException {
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        App app = new App("SPH Interpolation");
    }

    private void createUIComponents() {
        //Menu
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(menuBar, BorderLayout.NORTH);
        progressBar = new JProgressBar();

        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSimulation = new CreateSimulation();
                if (createSimulation.allData()) {
                    ImageManipulator imageManipulator = new ImageManipulator(createSimulation.getImageFile());
                    imageManipulator.autoCrop();
                    imageManipulator.thresholdFilter(10);
                    FEMesh mesh = new FEMesh(createSimulation.getFePointsFile());
                    imageManipulator.autoCrop();
                    Interpolation interpolation = new Interpolation(imageManipulator.getTempImage(), mesh);
                    progressBar.setIndeterminate(true);
                    interpolation.interpolate(createSimulation.getNgNumber(), createSimulation.getKernel());
                    File directory = new File(".");
                    String path = null;
                    try {
                        path = directory.getCanonicalPath() + "/";
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    PictureWithScale pictureWithScale = new PictureWithScale(path + "interpolation.txt");
                    pictureWithScale.drawOnImage();

                    progressBar.setIndeterminate(false);
                    JFrame result = new JFrame("Kernel function: " + createSimulation.getKernel().toString() + ", Number of neghbours: " + createSimulation.getNgNumber());
                    result.getContentPane().setLayout(new FlowLayout());
                    result.getContentPane().add(new JLabel(new ImageIcon(pictureWithScale.getPicture())));
                    result.pack();
                    result.setVisible(true);
                }
            }
        });
        menu.add(newMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new

                                               ActionListener() {
                                                   @Override
                                                   public void actionPerformed(ActionEvent e) {
                                                       System.exit(0);
                                                   }
                                               });
        menu.add(exitMenuItem);

    }
}
