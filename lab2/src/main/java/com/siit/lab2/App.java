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
    public static void main( String[] args )
    {
        int maxSteps = 20000;
        int dogs = 100;
        int gensPerDog = 1000;

        World world = new World(1000,1000);
        world.addHotDog(new HotDog(200, 800));

        world.addLake(new Lake(400, 400, 401, 601));
        world.addLake(new Lake(400,600,601,601));
        world.addLake(new Lake(600,400,601,601));
        world.calcDistField();
        ChromosomeManager chromosomeManager= new ChromosomeManager(dogs,gensPerDog);
        List<Chromosome> chromosomes = chromosomeManager.evolution(maxSteps,world);
        List<Chromosome> chromosomes2 = new ArrayList<Chromosome>();
        chromosomes2.add(chromosomes.get(chromosomes.size()-1));
        chromosomes.remove(chromosomes.size()-1);

        Plotter plotter = new Plotter(chromosomes,world,"some dogs");
        plotter.setTitle("Some dogs");
        plotter.setVisible(true);

        Plotter plotter2 = new Plotter(chromosomes2,world,"best");
        plotter2.setTitle("Lucky");
        plotter2.setVisible(true);


    }
}
