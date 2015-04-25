package com.siit.lab2;

import com.siit.lab2.core.*;

import javax.management.openmbean.SimpleType;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String FOLDER = "target/";
    static Random random = new Random();
    static int chromosome[] = new int[150];
    public static void main( String[] args )
    {
        for (int i = 0; i < chromosome.length; i++) {
            chromosome[i] = random.nextInt(16);
        }

        List<Integer> sets[] = new ArrayList[5];

        for (int i = 0; i < sets.length; i++) {
            sets[i] = new ArrayList<Integer>();
            for (int j = 0; j < 16; j++) {
                sets[i].add(j);
            }
            Collections.shuffle(sets[i]);
            System.out.println("set#" + i + arrayToString(sets[i]));

        }

        System.out.println("chromosome: "+Arrays.toString(chromosome));

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
            System.out.println("from "+arrayToString(i));
            System.out.println("  to "+arrayToString(mas));
            System.out.println("      fitness = "+f);
            System.out.println("real compares = "+compares);
            System.out.println();
            System.out.println("***********************");
            System.out.println();
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
}
