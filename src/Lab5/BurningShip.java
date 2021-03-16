package Lab5;

import Lab4.FractalGenerator;

import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {
    public static final int MAX_ITERATIONS = 200;

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    @Override
    public int numIterations(double x, double y) {
        int iteration = 0;
        double zReal = 0;
        double zImaginary = 0;

        while (iteration < MAX_ITERATIONS && zReal * zReal + zImaginary * zImaginary < 4)
        {
            double zRealUpdated = zReal * zReal - zImaginary * zImaginary + x;
            double zImaginaryUpdated = 2 * Math.abs(zReal) * Math.abs(zImaginary) + y;
            zReal = zRealUpdated;
            zImaginary = zImaginaryUpdated;
            iteration += 1;
        }

        if (iteration == MAX_ITERATIONS) return -1;
        return iteration;
    }

    @Override
    public String toString() {
        return "Burning Ship";
    }
}
