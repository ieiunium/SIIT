package com.siit.lab2.core;

/**
 * Created by kirill-good on 11.4.15.
 */
public class MyPoint2D {
    double x,y;
    public static double dist(MyPoint2D p1, MyPoint2D p2){
        double dx = p1.x-p2.x;
        double dy = p1.y-p2.y;
        double res = dx*dx+dy*dy;
        res = Math.sqrt(res);
        return res;
    }

    @Override
    public String toString() {
        return "MyPoint2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
