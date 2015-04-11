package com.siit.lab2.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by kirill-good on 11.2.15.
 */
public class ChromosomeManager {

    protected Chromosome chromosomes[] = null;
    protected FitnessFunction fitnessFunction;

    public ChromosomeManager(final int numberOfChromosome,int gensPerChromosome,FitnessFunction fitnessFunction){
        this.fitnessFunction = fitnessFunction;
        chromosomes = new Chromosome[numberOfChromosome];
        for(int i = 0;i< numberOfChromosome;i++){
            chromosomes[i]= new Chromosome(gensPerChromosome);
            chromosomes[i].setFitnessFunction(fitnessFunction);
        }
    }

    public void evolution(int steps){
        PrintWriter pw = null;
        PrintWriter pw2 = null;
        try {
            pw = new PrintWriter(new File("target/table.txt"));
            pw2 = new PrintWriter(new File("target/step-fitness.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Chromosome children[] = chromosomes.clone();
        for(int step = 0; step < steps; step++) {

            for(Chromosome i: chromosomes){
                i.calcFitness();
            }

            Arrays.sort(chromosomes);
            System.out.println(step + " " + chromosomes[0].fitness()+" "+ chromosomes[chromosomes.length-1].fitness());
            pw2.println(step + " " + chromosomes[0].fitness()+" "+ chromosomes[chromosomes.length-1].fitness());

            pw.println("step " + step+"\t"+ chromosomes[0].fitness());
            fitnessFunction.printDistTable(chromosomes[0],pw);
            pw.println(fitnessFunction.getPointsTable(chromosomes[0]));
            pw.println("==================");

            int half = chromosomes.length / 2;
            for (int i = 0; i < chromosomes.length; i++) {

                int i1 = Chromosome.random.nextInt(half);
                int i2;
                do{
                    i2 = Chromosome.random.nextInt(half);
                }while (i1==i2);
                Chromosome d1 = chromosomes[i1].getCopy();
                Chromosome d2 = chromosomes[i2].getCopy();
                children[i] = Chromosome.crossOver(d1, d2);
                //System.out.println("\t"+ Chromosome.dist(d1,d2));
            }
            Chromosome tmp[] = children;
            children = chromosomes;
            chromosomes = tmp;
        }
        pw.flush();
        pw.close();
        pw2.flush();
        pw2.close();
    }

    public Chromosome[] getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(Chromosome[] chromosomes) {
        this.chromosomes = chromosomes;
    }

    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
}
