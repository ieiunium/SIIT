package com.siit.lab2.core;

import java.awt.*;
import java.util.Random;

/**
 * Created by kirill-good on 9.2.15.
 */
public class Chromosome implements Comparable{
    protected int gens[];
    private int numberOfGens;
    private int fitness;
    public static final Random random = new Random();

    public Chromosome(final int numberOfGens){
        if(numberOfGens < 1){
            this.numberOfGens = 1000;
        }else {
            this.numberOfGens = numberOfGens;
        }
        gens = new int[numberOfGens];
        for(int i=0;i<gens.length;i++){
            gens[i] = random.nextInt(4);
        }
    }
    public void reset(Chromosome chromosome){
        for(int i=0;i<gens.length;i++){
            gens[i] = chromosome.gens[i];
        }
    }
    private Chromosome(){

    }
    public int calcFitness(World world){
        fitness = world.simulate(this,world);
        return fitness;
    }
    public int getFitness(){
        return fitness;
    }
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i:gens){
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }
    public Chromosome getCopy(){
        Chromosome chromosome = new Chromosome();
        chromosome.gens = this.gens.clone();
        chromosome.numberOfGens = this.numberOfGens;
        return chromosome;
    }
    public static Chromosome crossOver(Chromosome mother,Chromosome father){
        if(mother.numberOfGens!=father.numberOfGens){
            throw new RuntimeException("mother.lenth!=father.length");
        }
        Chromosome chromosome = mother.getCopy();
        for(int i = 0; i < mother.gens.length; i++){
            if(random.nextBoolean()){
                chromosome.gens[i] = mother.gens[i];
            }else {
                chromosome.gens[i] = father.gens[i];
            }
            if(random.nextInt(1000)<10){
                chromosome.gens[i] = random.nextInt(4);
            }
        }
        return chromosome;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Chromosome){
            return -((Chromosome)o).getFitness() + this.getFitness();
        }else {
            return 0;
        }
    }
    public void paint(Graphics g,int DX,int DY){

    }

    public static double s(double d){
        double sigma = 10;
        if(d<sigma){
            return 1-(d/sigma);
        }else{
            return 0;
        }
    }

    public static int dist(Chromosome c1, Chromosome c2){
        //TODO
        return 0;
    }
}
