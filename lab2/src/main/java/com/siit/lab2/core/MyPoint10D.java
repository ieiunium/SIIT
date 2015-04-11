package com.siit.lab2.core;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by kirill-good on 11.4.15.
 */
public class MyPoint10D {
    public double dimension[];
    public static Random random = new Random();

    public MyPoint10D() {
        this.dimension = new double[10];
        double S = 0;
        for (int i = 0; i < dimension.length; i++) {
            dimension[i] = random.nextDouble()*2 - 1;
            S += dimension[i]*dimension[i];
        }
        S = Math.sqrt(S);
        double S1 = 0;
        for (int i = 0; i < dimension.length; i++) {
            dimension[i] /= S;
            S1 += dimension[i]*dimension[i];
        }
        //System.out.println(S1);
    }

    public static double dist(MyPoint10D p1, MyPoint10D p2){
        if(p1.dimension.length!=p2.dimension.length){
            throw new RuntimeException("p1.dimension.length!+p2.dimension.length "+p1.dimension.length+" "+p2.dimension.length);
        }
        double res = 0;
        for (int i = 0; i < p1.dimension.length; i++) {
            res += Math.pow(p1.dimension[i] - p2.dimension[i], 2);
        }
        res = Math.sqrt(res);
        return res;
    }

    @Override
    public String toString() {
        return "MyPoint10D{" +
                "dimension=" + Arrays.toString(dimension) +
                '}';
    }
}
