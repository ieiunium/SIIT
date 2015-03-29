package com.company.mlp.math.genetics;

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
        double fit = 0;
        Chromosome children[] = chromosomes.clone();
        PrintWriter pwFitness = null;
        PrintWriter pwGeneration = null;
        try {

            pwFitness = new PrintWriter(new File("./target/fitness.txt"));
            pwGeneration = new PrintWriter(new File("./target/step.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int step = 0; step < steps; step++) {// || fit < 31.9

            for(Chromosome i: chromosomes){
                i.calcFitness();
            }

            Arrays.sort(chromosomes);
            System.out.println(step + " " + chromosomes[0].fitness()+" "+ chromosomes[chromosomes.length-1].fitness());
            if(step%1000==0){
                fitnessFunction.printTable(chromosomes[0],step);
            }
            pwFitness.println(chromosomes[0].fitness());
            pwGeneration.println(step);

            fit = chromosomes[0].fitness();
            if(fit > 31.9){
                pwFitness.flush();
                pwFitness.close();
                pwGeneration.flush();
                pwGeneration.close();
                return;
            }
            int half = chromosomes.length / 2;

            for (int i = 0; i < chromosomes.length; i++) {

                int i1 = Chromosome.random.nextInt(half);
                int i2;
                double s;
                do{
                    i2 = Chromosome.random.nextInt(half);
                }while (i1==i2);
                Chromosome d1 = chromosomes[i1];
                Chromosome d2 = chromosomes[i2];
                children[i] = Chromosome.crossOver(d1, d2);
            }
            Chromosome tmp[] = children;
            children = chromosomes;
            chromosomes = tmp;
        }
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
