package Lab4;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class JImageDisplay extends javax.swing.JComponent{
    private BufferedImage bufferedImage;

    public JImageDisplay(int width, int height){
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        super.setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        graphics.drawImage(bufferedImage, 0, 0,
                bufferedImage.getWidth(),
                bufferedImage.getHeight(), null);
    }

    public void clearImage(){
        bufferedImage.setRGB(0,0,0);
    }

    public void drawPixel(int x, int y, int rgbColor){
        bufferedImage.setRGB(x, y, rgbColor);
    }

    public BufferedImage getImage(){
        return bufferedImage;
    }
}
