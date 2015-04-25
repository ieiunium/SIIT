package com.siit.lab2;

import com.siit.lab2.core.*;

import javax.imageio.ImageIO;
import javax.management.openmbean.SimpleType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String FOLDER = "target/";
    static Random random = new Random();
    static int chromosome[] = new int[150];
    static PrintStream out = System.out;
    static{
        try {
            out = new PrintStream(new File("target/out.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main( String[] args )
    {
        for (int i = 0; i < chromosome.length; i++) {
            chromosome[i] = random.nextInt(16);
        }

        plot(chromosome);

        List<Integer> sets[] = new ArrayList[5];

        for (int i = 0; i < sets.length; i++) {
            sets[i] = new ArrayList<Integer>();
            for (int j = 0; j < 16; j++) {
                sets[i].add(j);
            }
            Collections.shuffle(sets[i]);
            out.println("set#" + i + arrayToString(sets[i]));

        }

        out.println("chromosome: "+arrayToString(chromosome));

        fitness(sets);
    }

    public static int fitness(List<Integer> sets[]){
        int res = 0;
        int mas[] = new int[16];
        for(List<Integer> i: sets){
            for (int j = 0; j < mas.length; j++) {
                mas[j] = i.get(j);
            }
            int compares = 0;
            for (int j = 0; j < chromosome.length; j+=2) {
                int it1 = chromosome[j];
                int it2 = chromosome[j+1];
                int i1 = Math.min(it1,it2);
                int i2 = Math.max(it1, it2);
                if(mas[i1]>mas[i2]){
                    int tmp = mas[i1];
                    mas[i1] = mas[i2];
                    mas[i2] = tmp;
                }
                if(i1!=i2){
                    compares++;
                }
            }
            int f = fitness(mas);
            out.println("before "+arrayToString(i));
            out.println(" after "+arrayToString(mas));
            out.println("      fitness = "+f);
            out.println("real compares = "+compares);
            out.println();
            out.println("***********************");
            out.println();
        }

        return res;
    }
    public static int fitness(int mas[]){
        int res = 0;
        for (int i = 0; i < mas.length; i++) {
            for (int j = i+1; j < mas.length; j++) {
                if(mas[j]>mas[i]){
                    res++;
                }
            }
        }
        return res;
    }
    public static int fitness(List<Integer> mas){
        int arr[] = new int[mas.size()];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = mas.get(j);
        }
        return fitness(arr);
    }

    static String arrayToString(List<Integer> arr){
        StringBuilder sb = new StringBuilder("[ ");
        for (int j = 0; j < arr.size(); j++) {
            String s = arr.get(j).toString();
            sb.append((s.length()==1?"0":"")+s+" ");
        }
        sb.append("]");
        return sb.toString();
    }

    static String arrayToString(int arr[]){
        StringBuilder sb = new StringBuilder("[ ");
        for (int j = 0; j < arr.length; j++) {
            String s = Integer.toString(arr[j]);
            sb.append((s.length()==1?"0":"")+s+" ");
        }
        sb.append("]");
        return sb.toString();
    }

    static void plot(int chromosome[]){
        BufferedImage image = new BufferedImage(1000,200,BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);

        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.BLACK);
        int H = 10;
        int W = 6;
        for (int i = 0; i < 16; i++) {
            int h = H*(i+1);
            g.drawLine(0,h,image.getWidth(),h);
        }

        for (int i = 0; i < chromosome.length; i+=2) {
            if(chromosome[i]!=chromosome[i+1]) {
                int x = (i + 1) * W;
                int y1 = (chromosome[i] + 1) * H;
                int y2 = (chromosome[i + 1] + 1) * H;
                drawArrowDown(g, x, Math.max(y1, y2));
                drawArrowUp(g, x, Math.min(y1, y2));
                g.drawLine(x, y1, x, y2);
            }
        }

        try {
            ImageIO.write(image,"png",new File("target/diagram.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void drawArrowUp(Graphics g,int x,int y){
        final int dx = 3;
        final int dy = 3;
        g.drawLine(x,y,x+dx,y+dy);
        g.drawLine(x,y,x-dx,y+dy);
    }
    static void drawArrowDown(Graphics g,int x,int y){
        final int dx = 3;
        final int dy = 3;
        g.drawLine(x,y,x+dx,y-dy);
        g.drawLine(x,y,x-dx,y-dy);
    }
}
