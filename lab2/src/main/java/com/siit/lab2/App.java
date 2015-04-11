package com.siit.lab2;

import com.siit.lab2.core.*;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String folder = "target/";
    public static void main( String[] args )
    {
        int maxSteps = 2000;
        int numOfChromosomes = 40;
        int numOfGens = 1000;


        ChromosomeManager chromosomeManager= new ChromosomeManager(numOfChromosomes,numOfGens,new FitnessFunction());
        chromosomeManager.evolution(maxSteps);

        /*int i1 = Chromosome.random.nextInt(chromosomes.size()-2)+1;
        int i2;
        do{
            i2 = Chromosome.random.nextInt(chromosomes.size()-2)+1;
        }while (i1 == i2);
        if(i1>i2){
            int tmp = i1;
            i1 = i2;
            i2 = tmp;
        }
        int index[] = {0
               ,i1
               ,i2
               ,chromosomes.size()-1
        };
        for(int i = 0; i<index.length;i++) {
            Plotter plotter = new Plotter(chromosomes.get(index[i]), world, "generation"+String.valueOf(index[i]));
            //plotter.setVisible(true);
        }*/
    }
}
