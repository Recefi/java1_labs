package org.recefi.exam;

public class Pnt {
    double x; double y;

    public Pnt(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    @Override
    public String toString() {
        return "Pnt{" + "x=" + x + ", y=" + y + '}';
    }
}
