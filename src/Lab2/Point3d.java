package Lab2;

public class Point3d extends Point2d {
    protected double z;

    public Point3d(double x,double y, double z) {
        super(x,y);
        this.z = z;
    }

    public Point3d(){
        this(0,0,0);
    }

    public double getZ() { return z; }
    public void setZ(double z) { this.z = z; }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Point3d)) return false;

        Point3d otherPoint  = (Point3d) o;

        return otherPoint.x == this.x
            && otherPoint.y == this.y
            && otherPoint.z == this.z;
    }

    public double distanceTo(Point3d otherPoint){
        return Math.abs(Math.pow(
                  Math.pow(this.x - otherPoint.x,2)
                + Math.pow(this.y - otherPoint.y,2)
                + Math.pow(this.z - otherPoint.z,2), 1.0 / 2));
    }
}
