package Lab4;

import Lab5.BurningShip;
import Lab5.Tricorn;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.*;
import javax.imageio.ImageIO.*;
import java.awt.image.*;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        displaySize = size;

        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }

    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());

        JFrame frame = new JFrame("Fractal");
        frame.add(display, BorderLayout.CENTER);

        addButtons(frame);
        addFractalList(frame);

        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void addFractalList(JFrame frame){
        JComboBox<FractalGenerator> comboBox = new JComboBox<>();
        comboBox.addItem(new Mandelbrot());
        comboBox.addItem(new Tricorn());
        comboBox.addItem(new BurningShip());
        comboBox.addActionListener(new SwitchFractalHandler());

        JLabel label = new JLabel("Fractal: ");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(label);
        buttonPanel.add(comboBox);
        frame.add(buttonPanel,BorderLayout.NORTH);
    }

    private void addButtons(JFrame frame){
        JButton resetButton = new JButton("Reset Display");
        JButton saveButton  = new JButton("Save Image");

        ButtonHandler buttonHandler = new ButtonHandler();

        resetButton.addActionListener(buttonHandler);
        saveButton.addActionListener(buttonHandler);

        resetButton.setActionCommand("reset");
        saveButton.setActionCommand("save");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);
        frame.add(buttonPanel,BorderLayout.SOUTH);
    }

    private void drawFractal()
    {
        for (int x = 0; x < displaySize; x++){
            for (int y = 0; y < displaySize; y++){

                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

                int iteration = fractal.numIterations(xCoord, yCoord);

                if (iteration == -1){
                    display.drawPixel(x, y, 0);
                }
                else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        display.repaint();
    }

    private class ButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){

            String command = e.getActionCommand();

            if(command.equals("reset")){
                fractal.getInitialRange(range);
                drawFractal();
            }
            else if (command.equals("save")){
                JFileChooser myFileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(filter);
                myFileChooser.setAcceptAllFileFilterUsed(false);

                int userSelection = myFileChooser.showSaveDialog(display);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        java.io.File file = myFileChooser.getSelectedFile();
                        BufferedImage displayImage = display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(display, exception.getMessage(),
                                "Failed to save image",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private class SwitchFractalHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JComboBox comboBox = (JComboBox) e.getSource();
            fractal = (FractalGenerator) comboBox.getSelectedItem();
            fractal.getInitialRange(range);
            drawFractal();
        }
    }

    private class MouseHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();

            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}
