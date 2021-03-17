package Lab4_6;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.image.*;
import java.io.File;

public class FractalExplorer {
    private int rowsRemaining;
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;
    private JFrame frame;

    public FractalExplorer(int size) {
        displaySize = size;

        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }

    /**Отвечает за графический интерфейс
     * Создает фрейм куда добавляет графические компоненты */
    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());

        frame = new JFrame("Fractal");
        frame.add(display, BorderLayout.CENTER);

        addButtons();
        addFractalList();

        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /** Добавляет комбобокс с фраклами*/
    private void addFractalList(){
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

    /** Добавляет кнопки */
    private void addButtons(){
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

    /** Рисует фрактал */
    private void drawFractal()
    {
        enableUI(false);
        rowsRemaining = displaySize;
        for (int y = 0; y < displaySize; y++){
            FractalWorker fractalWorker = new FractalWorker(y);
            fractalWorker.execute();
        }
        display.repaint();
    }

    /** Отключает пользовательский интерфейс(фрейм) */
    private void enableUI(boolean val){
        frame.setEnabled(val);
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
                        File file = myFileChooser.getSelectedFile();
                        BufferedImage displayImage = display.getImage();
                        ImageIO.write(displayImage, "png", file);
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

    /** Отвечает за приближение картинки фрактала */
    private class MouseHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if(rowsRemaining != 0) return;
            int x = e.getX();
            int y = e.getY();

            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    private class FractalWorker extends SwingWorker<Object,Object> {
        private int y;
        private int[] pixels;

        public FractalWorker(int y){
            this.y = y;
        }

        @Override
        protected Object doInBackground() {
            pixels = new int[displaySize];
            for (int x = 0; x < pixels.length; x++) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
                int iteration = fractal.numIterations(xCoord, yCoord);

                if (iteration == -1){
                    pixels[x] = 0;
                }
                else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    pixels[x] = rgbColor;
                }
            }

            return null;
        }

        @Override
        protected void done(){
            for (int x = 0; x < pixels.length; x++) {
                display.drawPixel(x, y, pixels[x]);
            }
            display.repaint(0,0 , y, displaySize,1);

            if(--rowsRemaining == 0){
                enableUI(true);
            }
        }
    }

    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}
