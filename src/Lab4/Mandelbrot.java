package Lab4;

import java.awt.geom.*;

public class Mandelbrot extends FractalGenerator{
    public static final int MAX_ITERATIONS = 200;

    public void getInitialRange(Rectangle2D.Double rect)
    {
        rect.x = -2;
        rect.y = -1.5;
        rect.width = 3;
        rect.height = 3;
    }

    public int numIterations(double x, double y)
    {
        int iteration = 0;
        double zReal = 0;
        double zImaginary = 0;

        while (iteration < MAX_ITERATIONS && zReal * zReal + zImaginary * zImaginary < 4)
        {
            double zRealUpdated = zReal * zReal - zImaginary * zImaginary + x;
            double zImaginaryUpdated = 2 * zReal * zImaginary + y;
            zReal = zRealUpdated;
            zImaginary = zImaginaryUpdated;
            iteration += 1;
        }

        if (iteration == MAX_ITERATIONS) return -1;
        return iteration;
    }
}
