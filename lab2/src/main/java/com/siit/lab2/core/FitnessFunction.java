package com.siit.lab2.core;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by kirill-good on 11.2.15.
 */
public class FitnessFunction {
    MyPoint10D target[] = new MyPoint10D[11];
    MyPoint2D walker[] = new MyPoint2D[target.length];

    double walkerDistMatrix[][] = new double[target.length][target.length];
    double distMatrix[][] = new double[target.length][target.length];
    public FitnessFunction(){
        for (int i = 0; i < target.length-1; i++) {
            target[i] = new MyPoint10D();
        }
        for (int i = 0; i < walker.length; i++) {
            walker[i] = new MyPoint2D();
        }
        target[target.length-1] = new MyPoint10D();
        for (int i = 0; i < target[target.length-1].dimension.length; i++) {
            target[target.length-1].dimension[i] = 0;
        }


        double max = 0;
        for (int i = 0; i < target.length; i++) {
            for (int j = 0; j < target.length; j++) {
                distMatrix[i][j] = MyPoint10D.dist(target[i], target[j]);
                if(distMatrix[i][j]>max){
                    max = distMatrix[i][j];
                }
                System.out.printf("%.2f\t", distMatrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        for (int i = 0; i < target.length; i++) {
            for (int j = 0; j < target.length; j++) {
                distMatrix[i][j] /= max;
                System.out.printf("%.2f\t",distMatrix[i][j]);
            }
            System.out.println();
        }
    }
    public double fitness(Chromosome chromosome){

        for (int i = 0; i < walker.length; i++) {
            walker[i].x = chromosome.gens[2*i];
            walker[i].y = chromosome.gens[2*i+1];
            //System.out.println(walker[i]);
        }
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        double max = 0;
        for (int i = 0; i < walker.length; i++) {
            for (int j = 0; j < walker.length; j++) {
                walkerDistMatrix[i][j] = MyPoint2D.dist(walker[i], walker[j]);
                if(walkerDistMatrix[i][j]>max){
                    max = walkerDistMatrix[i][j];
                }
            }
        }
        double res = 0;
        for (int i = 0; i < walker.length; i++) {
            for (int j = 0; j < walker.length; j++) {
                //walkerDistMatrix[i][j] /= max;
                res += Math.abs( walkerDistMatrix[i][j]/max-distMatrix[i][j] );
            }
        }
        if(Double.isNaN(res)){
            /*for (int i = 0; i < walker.length; i++) {
                for (int j = 0; j < walker.length; j++) {
                    System.out.printf("%.2f\t",walkerDistMatrix[i][j]);
                }
                System.out.println();
            }
            for (int i = 0; i < walker.length; i++) {
                System.out.println(walker[i]);
            }
            System.exit(0);*/
        }
        return res;
    }

    public String getPointsTable(Chromosome chromosome){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < walker.length; i++) {
            walker[i].x = chromosome.gens[2*i];
            walker[i].y = chromosome.gens[2*i+1];
            sb.append(String.valueOf(chromosome.gens[2*i]));
            sb.append("\t");
            sb.append(String.valueOf(chromosome.gens[2*i+1]));
            sb.append("\n");
        }
        return sb.toString();
    }

    public void printDistTable(Chromosome chromosome, PrintWriter pw){
        for (int i = 0; i < walker.length; i++) {
            walker[i].x = chromosome.gens[2*i];
            walker[i].y = chromosome.gens[2*i+1];

        }

        double max = 0;
        for (int i = 0; i < walker.length; i++) {
            for (int j = 0; j < walker.length; j++) {
                walkerDistMatrix[i][j] = MyPoint2D.dist(walker[i], walker[j]);
                if(walkerDistMatrix[i][j]>max){
                    max = walkerDistMatrix[i][j];
                }
            }
        }
        double res = 0;
        for (int i = 0; i < walker.length; i++) {
            for (int j = 0; j < walker.length; j++) {
                walkerDistMatrix[i][j] /= max;
                pw.printf("%.2f\t",walkerDistMatrix[i][j]);
            }
            pw.println();
        }
    }
}
