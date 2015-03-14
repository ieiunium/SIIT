package com.siit.lab2;

import com.siit.lab2.core.*;
import com.siit.lab2.swing.Plotter;

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
        int maxSteps = 20000;
        int dogs = 40;
        int gensPerDog = 1000;

        World world = new World(1000,1000);
        world.addHotDog(new HotDog(200, 200));
        world.addHotDog(new HotDog(200, 800));
        world.addHotDog(new HotDog(800, 200));
        world.addHotDog(new HotDog(800, 800));

        ChromosomeManager chromosomeManager= new ChromosomeManager(dogs,gensPerDog);
        List<List<Chromosome>> chromosomes = chromosomeManager.evolution(maxSteps,world);
        int index[] = {
                0
               ,Chromosome.random.nextInt(chromosomes.size()-2)+1
               ,Chromosome.random.nextInt(chromosomes.size()-2)+1
               ,chromosomes.size()-1
        };
        for(int i = 0; i<index.length;i++) {
            Plotter plotter = new Plotter(chromosomes.get(index[i]), world, "generation"+String.valueOf(i));
            plotter.setTitle("Some dogs");
            //plotter.setVisible(true);
        }
    }
}
