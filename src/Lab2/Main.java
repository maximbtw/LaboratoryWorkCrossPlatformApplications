package Lab2;

import java.util.Scanner;

/**
 * Лабораторные работа N2 по дисциплине "кроссплатформенные приложения"
 *
 * @author maxim kozlov bst - 1902
 * @version dated 01.03.2021
 */

public class Main {
    public static void main(String[] args){
        Point3d point1 = new Point3d(0.5,0.5,0.5);
        Point3d point2 = new Point3d();
        Point3d point3 = writePoint();

        if(point1.equals(point2) || point1.equals(point3) || point2.equals(point3)){
            System.out.println("Some point are equals");
        }
        else {
            System.out.println("Area: " + computeArea(point1, point2, point3));
        }
    }

    public static double computeArea(Point3d point1, Point3d point2, Point3d point3){
        double a = point1.distanceTo(point2);
        double b = point2.distanceTo(point3);
        double c = point3.distanceTo(point1);
        double p = (a + b + c ) / 2;

        return Math.pow(p * (p - a) * (p - b) * (p - c), 1.0 / 2);
    }

    private static Point3d writePoint(){
        Scanner input = new Scanner(System.in);

        System.out.println("Enter x:");
        double x = input.nextDouble();
        System.out.println("Enter y:");
        double y = input.nextDouble();
        System.out.println("Enter z:");
        double z = input.nextDouble();

        return new Point3d(x,y,z);
    }
}
