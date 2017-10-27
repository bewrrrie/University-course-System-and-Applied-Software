package com.examples.leshkov.sippo.math;

import static com.examples.leshkov.sippo.math.Constants.POINT_EPSILON;

public class Point2D {
    private double x, y;

    public Point2D() {
        this.x = 0;
        this.y = 0;
    }

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public void moveTo(double newX, double newY) {
        x = newX;
        y = newY;
    }

    public void print() {
        System.out.println("(" + x + ", " + y + ")");
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2D p = (Point2D) o;

        return 	Math.abs(x - p.getX()) <= POINT_EPSILON &&
				Math.abs(y - p.getY()) <= POINT_EPSILON;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
