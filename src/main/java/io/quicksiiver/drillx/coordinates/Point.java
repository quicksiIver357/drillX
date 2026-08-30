package main.java.io.quicksiiver.drillx.coordinates;

import java.util.Arrays;

import main.java.io.quicksiiver.drillx.field.RotationDirection;

public class Point {
    // variables
    private double x;
    private double y;

    // CONSTRUCTORS
    public Point(double x, double y) {
        setPos(x, y);
    }
    public Point(double[] pos) {
        setPos(pos);
    }
    // copy constructor
    public Point(Point point) {
        setPos(point);
    }

    // GETTERS
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double[] getPos() {
        double[] pos = {getX(), getY()};
        return pos;
    }

    // SETTERS
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setPos(double[] pos) {
        // data validation
        if (!validateDoubleArray(pos)) {
            throw new IllegalArgumentException("double[] pos should be of length 2 and contain x, y");
        } else {
            setPos(pos[0], pos[1]);
        }
    }
    public void setPos(double x, double y) {
        setX(x);
        setY(y);
    }
    public void setPos(Point pos) {
        setPos(pos.getPos());
    }

    // .equals
    public boolean equals(Point pos) {
        return equals(pos.getPos());
    }
    public boolean equals(double[] pos) {
        return Arrays.equals(pos, getPos());
    }

    // MODIFIERS
    public void applySimpleDirectionalMovement(RotationDirection rd, double ma) { // rd rotationDirection, ma movementAmount
        // applies a movement amount to a position using a RotationDirection object to choose which way to go
        if (rd.equals(RotationDirection.NORTH) || rd.equals(RotationDirection.NORTHEAST) || rd.equals(RotationDirection.NORTHWEST)) {
            this.setY(this.getY() - ma);
        } if (rd.equals(RotationDirection.SOUTH) || rd.equals(RotationDirection.SOUTHEAST) || rd.equals(RotationDirection.SOUTHWEST)) {
            this.setY(this.getY() + ma);
        } if (rd.equals(RotationDirection.EAST) || rd.equals(RotationDirection.NORTHEAST) || rd.equals(RotationDirection.SOUTHEAST)) {
            this.setX(this.getX() + ma);
        } if (rd.equals(RotationDirection.WEST) || rd.equals(RotationDirection.NORTHWEST) || rd.equals(RotationDirection.SOUTHWEST)) {
            this.setX(this.getX() - ma);
        }
    }
    public void translate(double dx, double dy) {
        setPos(getX() + dx, getY() + dy);
    }
    public void translate(double[] d) { // difference d
        if (validateDoubleArray(d)) {
            setX(getX() + d[0]);
            setY(getY() + d[1]);
        } else {
            throw new IllegalArgumentException("double[] d should be of length 2 and contain x, y");
        }
    }
    

    // other
    public static boolean validateDoubleArray(double[] array) {
        if (array.length != 2) {
            return false;
        } else {
            return true;
        }
    }
    
}
