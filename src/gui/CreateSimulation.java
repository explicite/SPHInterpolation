package gui;

import core.interfaces.KernelFunction;
import core.kernel.BSpline;
import core.kernel.GaussianKernel;
import core.kernel.QuinticSpline;

import javax.swing.*;
import java.awt.event.*;

public class CreateSimulation extends JDialog {
    private final JFileChooser fc = new JFileChooser();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField fePoints;
    private JButton imageButton;
    private JButton feButton;
    private JComboBox funComboBox;
    private JTextField ngNum;
    private JTextField image;
    private Boolean validateFlag = false;

    public CreateSimulation() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnResult = fc.showOpenDialog(CreateSimulation.this);

                if (returnResult == JFileChooser.APPROVE_OPTION) {
                    image.setText(fc.getSelectedFile().toString());
                }
            }
        });

        feButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnResult = fc.showOpenDialog(CreateSimulation.this);

                if (returnResult == JFileChooser.APPROVE_OPTION) {
                    fePoints.setText(fc.getSelectedFile().toString());
                }
            }
        });
        buttonOK.addActionListener(new

                                           ActionListener() {
                                               public void actionPerformed(ActionEvent e) {
                                                   onOK();
                                               }
                                           });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new

                                  WindowAdapter() {
                                      public void windowClosing(WindowEvent e) {
                                          onCancel();
                                      }
                                  });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new

                                                   ActionListener() {
                                                       public void actionPerformed(ActionEvent e) {
                                                           onCancel();
                                                       }
                                                   }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
    }

    public String getFePointsFile() {
        return fePoints.getText();
    }

    public String getImageFile() {
        return image.getText();
    }

    public Integer getNgNumber() {
        return Integer.parseInt(ngNum.getText());
    }

    public KernelFunction getKernel() {
        switch (funComboBox.getSelectedIndex()) {
            case 1:
                return new BSpline();
            case 2:
                return new QuinticSpline();
        }
        return new GaussianKernel();
    }

    private void onOK() {
// add your code here
        this.dispose();
    }

    private void onCancel() {
// add your code here if necessary
        this.dispose();
    }

    private void createUIComponents() {
        String[] functions = {"Gaussian Kernel", "B-Spline", "Quintic Spline"};
        funComboBox = new JComboBox(functions);
    }

    public Boolean allData() {
        if (image.getText().isEmpty() || ngNum.getText().isEmpty())
            return false;
        else
            return true;
    }
}
